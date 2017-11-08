cmake_minimum_required(VERSION 2.8)
project(Source.cpp)
SET(srcs
	Source.cpp
)
SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra -Werror --std=c++14 -pthread")
ADD_EXECUTABLE(thread ${srcs})
