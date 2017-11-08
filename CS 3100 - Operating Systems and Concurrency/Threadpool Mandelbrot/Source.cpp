#include <iostream>
#include <queue>
#include <thread>
#include <atomic>
#include <map>
#include <mutex>
#include <fstream>
#include <string>
#include <math.h>

template<typename T>
class TSQ {
public:
	void enqueue(T t) {
		std::lock_guard<std::mutex> l(m);
		q.push(t);
	}

	T dequeue() {
		std::lock_guard<std::mutex> l(m);
		auto res = q.front();
		q.pop();
		return res;
	}
	//Better than above
	bool try_dequeue() {
		std::lock_guard<std::mutex> l(m);
		if (q.empty()) return false;
		return true;
	}

	bool is_empty() {
		return this.try_dequeue();
	}
private:
	std::queue<T> q;
	std::mutex m;
};

// Day 2
/*
// How to wait on a condition
std::condition_variable c;
std::mutex m;

// unique_lock is like lock_guard, but provides access to lock() and unlock()
std::unique_lock<std::mutex> l(m);
// You must acquire a mutex before you call wait
// wait() will release the mutex, go into the wait condition, and reaquire the lock before returning
// You must acquire the lock, recheck the condition, then wait, which can be done with a while loop
//while (c) c.wait(l);

// How to signal condition vars

c.notify();
c.notify_all();


// Handy way to associate mutexes with the data they protect
template<typename T>
class Mutexed {
std::mutex m;
T data
// create some functions here to modify the data, locking and unlocking it appropriately
};
*/

TSQ <std::function<void(void)>> q;
class ThreadPool {
	using func = std::function<void(void)>;
public:

	ThreadPool(int n) :/*q(),*/ pool(n), /*hasItem(),*/ itemMutex(), shouldContinue(true) {
		for (auto& t : pool) {
			t = std::thread([=]() {this->run(); });
		}
	}

	~ThreadPool() {
		for (auto& t : pool) t.join();
	}

	void post(func f) {
		{//Critical section
			q.enqueue(f);
			//hasItem.notify_one();
		}
	}

	void run() {
		while (shouldContinue) {
			func f;
			while (!q.try_dequeue()) {
				std::unique_lock<std::mutex> l(itemMutex);
				//hasItem.wait(l);
				stop();
				if (!shouldContinue) return;
			}
			f = q.dequeue();
			f();
		}
	}
	/*
	void then(func f, int id) {
	std::lock_guard<std::mutex> l(m);
	//The continuation could do something like take a timestamp and record it in a global scope
	// We don't *need* continuations for this assn
	continuations[id]();
	}
	*/
	void stop() {
		shouldContinue = false;
		//hasItem.notify_all();
	}

private:
	//TSQ<func> q;
	//std::condition_variable hasItem;
	std::vector<std::thread> pool;
	std::mutex itemMutex;
	std::atomic<bool> shouldContinue;
	std::map<int, int> counts;
	std::map<int, func> continuations;
	std::mutex m;
};

struct Color {
	int R, G, B;
};

const int WIDTH = 256, HEIGHT = 256, SEQS = 50;
int colorVal = 256;
int maxVal = 255, maxIter = 25;
const int nThreads = 4, nRows = 16;
double minReal = -1.5, maxReal = 0.7, minIm = -1.0, maxIm = 1.0;
std::vector<std::vector<Color>>pixel(HEIGHT, std::vector<Color>(WIDTH));

template <typename T>
auto timeFunc(T f) {
	auto start = std::chrono::steady_clock::now();
	f();
	auto end = std::chrono::steady_clock::now();
	auto ms = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);
	return ms.count();
}
int determineBeg(int thr) {
	int H = HEIGHT / nThreads;
	return H*thr;
}
int determineEnd(int thr) {
	int H = HEIGHT / nThreads;
	return (H*(thr + 1));
}

double getR(int x, int w, double minR, double maxR) {
	double range = maxR - minR;
	return x * (range / w) + minR;
}

double getI(int y, int h, double minI, double maxI) {
	double range = maxI - minI;
	return y * (range / h) + minI;
}
int findMandle(double R, double I, int max_iter) {
	double zR = 0.0, zI = 0.0;
	int i = 0;
	while (i < max_iter && pow(zR, 2) + pow(zI, 2) < 4.0) {
		double temp = pow(zR, 2) - pow(zI, 2) + R;
		zI = 2.0 * zR * zI + I;
		zR = temp;
		i++;
	}
	return i;
}
double getAverage(int arr[], const int SIZE) {
	double a = 0;
	for (int i = 0; i < SIZE; i++) {
		a += arr[i];
	}
	return a / SIZE;
}
double getStdDev(int arr[], const int SIZE, double avg) {
	auto num2 = 0;
	for (int i = 0; i<SIZE; i++) {
		num2 += (int)pow((avg - arr[i]), 2);
	}
	double result = sqrt(num2 / SIZE);
	return result;
}
void doMandleSection(int begH, int endH) {
	for (int y = begH; y < endH; y++) {
		for (int x = 0; x < WIDTH; x++) {
			double real = getR(x, WIDTH, minReal, maxReal);
			double imaginary = getI(y, HEIGHT, minIm, maxIm);
			int n = findMandle(real, imaginary, maxIter);
			pixel[y][x].R = ((n*n) % 256);
			pixel[y][x].G = (n % 256);
			pixel[y][x].B = (n % 256);
		}
	}
}



void splitIntoRows(){
	/*
	int determineBeg(int thr) {
		int H = HEIGHT / nThreads;
		return H*thr;
	}
	int determineEnd(int thr) {
		int H = HEIGHT / nThreads;
		return (H*(thr + 1));
	}
	*/

	int remainingRows = HEIGHT;
	for (int i=0;remainingRows>0;remainingRows-=nRows, i++) {
		int rowSize = nRows;
		q.enqueue([i, rowSize]() {doMandleSection((i*rowSize), (rowSize*(i + 1))); });  ;
		//std::cout << i<<", "<<remainingRows << std::endl;
	}

}

void threadedStuff() {
	/*
	for (int i = 0; i < nThreads; i++) {
		int beg = determineBeg(i);
		int end = determineEnd(i);
		q.enqueue([beg, end]() {(doMandleSection(beg, end)); });
	}
	*/
	splitIntoRows();
	ThreadPool TP(nThreads);
}



void doMandle() {
	for (int y = 0; y < HEIGHT; y++) {
		for (int x = 0; x < WIDTH; x++) {
			double real = getR(x, WIDTH, minReal, maxReal);
			double imaginary = getI(y, HEIGHT, minIm, maxIm);
			int n = findMandle(real, imaginary, maxIter);
			pixel[y][x].R = ((n*n) % 256);
			pixel[y][x].G = (n % 256);
			pixel[y][x].B = (n % 256);
		}
	}
}

void printFile(std::string fileName, std::string t, int WIDTH, int HEIGHT, int colorVal, std::ofstream & fout) {
	fout.open(fileName);
	fout << t << std::endl;
	fout << WIDTH << "," << HEIGHT << std::endl;
	fout << colorVal << std::endl;
}
void printMandle(std::vector<std::vector<Color>> pixel, int H, int W, std::ofstream & fout) {
	for (int y = 0; y < H; y++) {
		for (int x = 0; x < W; x++) {
			fout << pixel[y][x].R << " " << pixel[y][x].G << " " << pixel[y][x].B << " ";
		}
		fout << std::endl;
	}
}


void doIt() {
	std::ofstream fout;
	int totalTime = 0;
	int results[SEQS];
	for (int i = 0; i < SEQS; i++) {
		int time = (int)timeFunc(threadedStuff);
		results[i] = time;
		totalTime += time;
	}
	printFile("output_image.ppm", "P3", WIDTH, HEIGHT, colorVal, fout);
	printMandle(pixel, HEIGHT, WIDTH, fout);
	std::cout << "It took " << totalTime << " ms to run with "<<nThreads<<" threads and with rows with a thickness of "<<nRows <<"."<< std::endl;
	double AVG = getAverage(results, SEQS);
	std::cout << "On average, each run took " << AVG << " ms, with a Standard Deviation of " << getStdDev(results, SEQS, AVG) << " ms." << std::endl;
}

int main() {
	doIt();
	return 0;
}