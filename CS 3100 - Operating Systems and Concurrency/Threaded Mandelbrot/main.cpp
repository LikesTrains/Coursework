#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <chrono>
#include <string>
#include <thread>

struct Color {
	int R, G, B;
};

const int WIDTH = 256, HEIGHT = 256, SEQS = 20;
int colorVal = 256;
int maxVal = 255, maxIter = 25;
const int nThreads = 4;
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
	for (int i = 0; i < SIZE;i++) {
		a += arr[i];
	}
	return a / SIZE;
}
double getStdDev(int arr[],const int SIZE, double avg) {
	auto num2 = 0;
	for (int i = 0; i<SIZE; i++) {
		num2 += (int)pow((avg - arr[i]), 2);
	}
	auto result = sqrt(num2 / SIZE);
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
void threadedStuff() {
	std::thread threads [nThreads];

	for (int i = 0; i < nThreads;i++) {
		int beg = determineBeg(i);
		int end = determineEnd(i);
		threads[i] = std::thread(doMandleSection,beg, end);
	}
	for (int i = 0; i < nThreads; i++) {
		threads[i].join();
	}
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
	for (int i = 0; i < SEQS;i++) {
		int time = (int)timeFunc(threadedStuff);
		results[i] = time;
		totalTime += time;
	}
	printFile("output_image.ppm", "P3", WIDTH, HEIGHT, colorVal, fout);
	printMandle(pixel, HEIGHT, WIDTH, fout);
	std::cout << "It took " << totalTime << " ms." << std::endl;
	double AVG = getAverage(results, SEQS);
	std::cout << "On average, each run took " << AVG << " ms, with a Standard Deviation of " << getStdDev(results, SEQS, AVG) << " ms." << std::endl;
}
int main() {
	doIt();
	return 0;
}