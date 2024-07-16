cmake_minimum_required(VERSION 3.10)

# Project name and version
project(MachineLearningProject VERSION 1.0)

# Set the C++ standard
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED True)

# Add the math_lib library
add_library(math_lib
    math_lib/src/matrix.cpp
    math_lib/src/math_lib.cpp
    # Add other source files for math_lib here
)

# Add include directories for math_lib
target_include_directories(math_lib PUBLIC math_lib/include)

# Add the models library
add_library(models
    models/src/Linear\ Regression/Normal\ Equation/linear_regression.cpp
    models/src/Logistic\ Regression/logistic_regression.cpp
    # Add other source files for models here
)

# Add include directories for models
target_include_directories(models PUBLIC models/include)

# Link math_lib to models
target_link_libraries(models PRIVATE math_lib)

# Add the main executable
add_executable(main
    main.cpp
)

# Link models and math_lib to the main executable
target_link_libraries(main PRIVATE models math_lib)
