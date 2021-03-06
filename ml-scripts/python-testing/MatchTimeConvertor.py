#! /usr/bin/python
#
# Copyright 2014 All Rights Reserved
# Author: Robert Bezirganyan (robbez@uw.edu)

import sys

# compatability check taken from http://stackoverflow.com/a/29688081/3791964
# AWS uses Python2 by default, which this can cause backwards compatibility issues
is_py2 = sys.version[0] == '2'
if is_py2:
    import Queue as queue
else:
    import queue as queue

"""
Class that converts the match file given into queues that can be used.
"""
class MatchConverter:
    def __init__(self, file, offset):
        self.matchFile = file
        self.matchOffset = offset


    def toMilliseconds(self, time):
        totalMilliseconds = 0
        timeComponents = time.split(":")
        hours = int(timeComponents[0])
        minutes = int(timeComponents[1])
        secondsAndMillis = timeComponents[2].split(",")

        seconds = int(secondsAndMillis[0])
        milliseconds = int(secondsAndMillis[1])

        totalMilliseconds = hours * 3600000 + minutes * 60000 + seconds * 1000 + milliseconds

        return totalMilliseconds


    def convert(self):
        print("Convert Called")
        with open(self.matchFile, 'r') as match:

            timesList = queue.Queue()

            for line in match:
                splitLine = line.split("-->");

                if len(splitLine) > 1:
                    startTime = splitLine[0].rstrip();
                    endTime = splitLine[1].rstrip();
                    # print startTime + "---->" + endTime
                    startMills = self.toMilliseconds(startTime) - self.matchOffset
                    endMills = self.toMilliseconds(endTime) - self.matchOffset

                    print(str(startMills) + "\t" + str(endMills))

                    timesList.put([startMills, endMills])

        return timesList










