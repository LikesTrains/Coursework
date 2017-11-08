#include <iostream>
#include <queue>
#include <thread>
#include <atomic>
#include <map>
#include <mutex>
#include <fstream>
#include <string>
#include <math.h>
#include <forward_list>
#include <condition_variable>
//#include <std::random_shuffle>
//#include <std::find>
//#include <std::partition>

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

std::vector<int> searchableDuderino;
void makeDuderino(int VECTOR_SIZE = 128) {
	for (int i = 0; i < VECTOR_SIZE;i++) {
		searchableDuderino.push_back(i);
	}
	std::random_shuffle(searchableDuderino.begin(), searchableDuderino.end());
}


std::mutex coutM;
std::atomic<bool> found=false;
using func = std::function<void(void)>;
TSQ <std::function<void(void)>> q;
std::condition_variable hasItem;
class ThreadPool {	
public:

	ThreadPool(int n, std::queue <func> inQ) :/*q(),*/ pool(n), /*hasItem(),*/ itemMutex(), shouldContinue(true) {
		while (inQ.empty()!=true) {
			q.enqueue(inQ.front());
			inQ.pop();
		}
		for (auto& t : pool) {
			t = std::thread([=]() {this->run(); });
		}
	}
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
			hasItem.notify_one();
		}
	}

	void run() {
		while (shouldContinue) {
			func f;
			while (!q.try_dequeue()) {
				std::unique_lock<std::mutex> l(itemMutex);
				hasItem.wait(l, [=]() {return workingThreads >= 0; });
				stop();
				if (!shouldContinue) return;
			}
			workingThreads++;
			f = q.dequeue();
			f();
			workingThreads--;
			if (found==true) {
				stop();
			}
			if (workingThreads==0&&!q.try_dequeue()) {
				stop();
			}
		}
	}
	void stop() {
		shouldContinue = false;
		hasItem.notify_all();
	}

private:
	//TSQ<func> q;
	//std::condition_variable hasItem;
	std::vector<std::thread> pool;
	std::mutex itemMutex;
	std::atomic<bool> shouldContinue;
	std::atomic<int> workingThreads;
	std::map<int, int> counts;
	std::map<int, func> continuations;
	std::mutex m;
}; 
template <typename T>
auto timeFunc(T f) {
	auto start = std::chrono::steady_clock::now();
	f();
	auto end = std::chrono::steady_clock::now();
	auto ms = std::chrono::duration_cast<std::chrono::microseconds>(end - start);
	return ms.count();
}
double getAverage(int arr[], const int SIZE) {
	double a = 0;
	for (int i = 0; i < SIZE; i++) {
		a += arr[i];
	}
	return a / SIZE;
}
double getStdDev(int arr[], const int SIZE, double avg) {
	double num2 = 0;
	for (int i = 0; i<SIZE; i++) {
		num2 += (double)pow((avg - arr[i]), 2);
	}
	double result = sqrt(num2 / SIZE);
	return result;
}

const int SEQS = 10;
int vectorSize = 128, nThreads = 2, valueSearched = 40;

void threadedLinear(){
	makeDuderino(vectorSize);
	int partSize = vectorSize / nThreads;
	std::queue<func> toBeAdded;
	std::vector<int> smaller(partSize);
	for (int i = 0; i < nThreads;i++) {
		for (int j = 0; j < partSize;j++) {
			smaller[j] = searchableDuderino[j + (i*partSize)];
			//std::cout << smaller[j] << " ";
		}
		
		toBeAdded.push(
					[smaller](){
						auto it = std::find(smaller.begin(), smaller.end(), valueSearched);
						if (it != smaller.end()) { /*std::cout << "F" << std::endl;*/ found = true; }
						/*else std::cout << "D" << std::endl;*/
					}
				);
				
		//std::cout << std::endl;
	}
	ThreadPool TP(nThreads, toBeAdded);
}

void printL(int totalTime, int results[]) {

	std::lock_guard<std::mutex> lockC(coutM);
	std::cout << "It took " << totalTime << " microseconds to run with " << nThreads << " threads with a vector size of " << vectorSize << "." << std::endl;
	double AVG = getAverage(results, SEQS);
	std::cout << "On average, each run took " << AVG << " microseconds, with a Standard Deviation of " << getStdDev(results, SEQS, AVG) << " microseconds." << std::endl;

}
void linearStuff() {
	
	int totalTime = 0;
	int results[SEQS];
	for (int i = 0; i < SEQS; i++) {
		int time = (int)timeFunc(threadedLinear);
		found = false;
		results[i] = time;
		totalTime += time;
	}
	printL(totalTime, results);
}
void post(func f) {
	{
		q.enqueue(f);
		hasItem.notify_one();
	}
}

template <class ForwardIt>
void quicksort(ForwardIt first, ForwardIt last)
{
	if (first == last) return;
	auto pivot = *std::next(first, std::distance(first, last) / 2);
	ForwardIt middle1 = std::partition(first, last,
		[pivot](const auto& em) { return em < pivot; });
	ForwardIt middle2 = std::partition(middle1, last,
		[pivot](const auto& em) { return !(pivot < em); });
	post([=]() {quicksort(first, middle1); });
	post([=]() {quicksort(middle2, last); });
}


void threadedQuick() {
	makeDuderino(vectorSize);	
	std::queue<func> toBeAdded;
	auto first = searchableDuderino.begin();
	auto last = searchableDuderino.end();
	toBeAdded.push(
		[=]() {
		if (first == last) return;
		auto pivot = *std::next(first, std::distance(first, last) / 2);
		auto middle1 = std::partition(first, last,
			[pivot](const auto& em) { return em < pivot; });
		auto middle2 = std::partition(middle1, last,
			[pivot](const auto& em) { return !(pivot < em); });
		post([=]() {quicksort(first, middle1); });
		post([=]() {quicksort(middle2, last); });
	});

	ThreadPool TP(nThreads, toBeAdded);

	/*for (int i = 0; i < searchableDuderino.size()-1;i++) {
		std::cout << searchableDuderino.at(i) << " ";
	}*/
}
void quickStuff() {
	
	int totalTime = 0;
	int results[SEQS];
	for (int i = 0; i < SEQS; i++) {
		int time = (int)timeFunc(threadedQuick);		
		results[i] = time;
		totalTime += time;
	}
	//printL(totalTime, results);
	std::cout << "This was a triumph" << std::endl;
}


int main() {
	linearStuff();
	//quickStuff();


	return 0;
}