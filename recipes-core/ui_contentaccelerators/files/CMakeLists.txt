PROJECT(chromium-ui-content-accelerators CXX)

CMAKE_MINIMUM_REQUIRED(VERSION 2.8.8)
SET(CMAKE_BUILD_TYPE Debug)
SET(CMAKE_POSITION_INDEPENDENT_CODE ON)

SET(PROJECT_NAME "Chromium - UI Content Accelerators")

# Verify a backend (X11/OZONE) has been chosen ; default to OZONE
IF(NOT BACKEND)
  MESSAGE(STATUS "Option \"-DBACKEND=<X11/OZONE>\" not passed; defaulting to OZONE...")
  SET(BACKEND "OZONE")
ENDIF(NOT BACKEND)
MESSAGE(STATUS "Chosen Backend: ${BACKEND}")

# CMake modules
INCLUDE(GNUInstallDirs)

# Optional dependencies and definitions
IF(BACKEND STREQUAL "OZONE")
  ADD_DEFINITIONS(-DUSE_OZONE)
ENDIF(BACKEND STREQUAL "OZONE")

IF(BACKEND STREQUAL "X11")
  ADD_DEFINITIONS(-DUSE_X11)
ENDIF(BACKEND STREQUAL "X11")

# Common definitions
ADD_DEFINITIONS(-DUSE_AURA)

IF(CMAKE_COMPILER_IS_GNUCXX)
    ADD_DEFINITIONS(-std=c++11)
ENDIF()


# SOURCES FILE LIST
ADD_LIBRARY(src OBJECT accelerator_util.cc)


# all files refer to "ui/...", so we include "../.." here
SET(include_dirs ${INCLUDE_DIRS} ${CMAKE_SOURCE_DIR}/../..)

ADD_LIBRARY(ui_content_accelerators SHARED $<TARGET_OBJECTS:src>)
INCLUDE_DIRECTORIES(${include_dirs})
TARGET_LINK_LIBRARIES(ui_content_accelerators ${LINK_LIBRARIES})

INSTALL(TARGETS ui_content_accelerators
        LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}/chromium)
