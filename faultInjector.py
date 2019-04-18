#!/usr/bin/python2.7
from math import ceil
import subprocess
import datetime

from fabric.colors import red

## disk fault injector : chmod: Unable to change file mode on .: Operation not permitte -- in /
## need to inject in /var/root
def introduceFault(faultcommand, healthcommand, heatlhintervalInSeconds, healthyThreshold, unhealthyThreshold):


    print("\nhealthyThreshold --> {}".format(healthyThreshold))
    print("unhealthyThreshold --> {}".format(unhealthyThreshold))
    print("Injecting call --> {}".format(faultcommand))

    starttime = datetime.datetime.now()
    print("\nHealthInterval in seconds --> {}".format(heatlhintervalInSeconds))
    ## below duration will be based on heatlhintervalInSeconds
    heatlhintervalInSeconds = (heatlhintervalInSeconds * unhealthyThreshold)

    duration = datetime.timedelta(seconds=heatlhintervalInSeconds)
    endTime = starttime + duration
    print("Fault injection duration in seconds -->   {}".format(duration.seconds))
    firstBreakOutFlag = True
    while('true'):

        subprocess.check_output(['bash','-c', faultcommand], stderr=subprocess.STDOUT)
        output = subprocess.check_output(['bash','-c', healthcommand], stderr=subprocess.STDOUT)

        if("disk" in faultcommand and "500" in output): break
        firstBreakOutFlag = firstBreak(output, firstBreakOutFlag, starttime)


        if "503" in output or ("500" in output and datetime.datetime.now() >= endTime):
            break

    print("---fault injection completed ---")

    unhealthyThresholdCounter = 0
    healthyThresholdCounter = 0

    startpinginghealthcheck = datetime.datetime.now()
    print("---start pinging health check ---")
    while('true'):

        # print time taken if it reaches unhealthy threshold
        if(unhealthyThresholdCounter == unhealthyThreshold):
            print("unhealthy threshold reached --> {}".format(datetime.datetime.now() - startpinginghealthcheck))

        output = subprocess.check_output(['bash','-c', healthcommand], stderr=subprocess.STDOUT)

        ## break out if node comes back healthy after faulty injection
        if "200" in output and healthyThresholdCounter == healthyThreshold:
            currenttime = datetime.datetime.now()
            t = currenttime - startpinginghealthcheck
            print("timetaken to come back  {}".format(t))
            break

        ## maintain counters in order to detect breach time
        if("500" in output):
            unhealthyThresholdCounter += 1

        if("503" in output):
            break
        if("200" in output):
            healthyThresholdCounter += 1

        ## for memory fault calls, reset memory after threshold crosses heatlhintervalInSeconds otherwise it will be infinite calls
        if((datetime.datetime.now() - startpinginghealthcheck).total_seconds() > heatlhintervalInSeconds + 5 and "memory" in faultcommand):
            subprocess.check_output(['bash','-c', faultcommand + "/reset"], stderr=subprocess.STDOUT)

def firstBreak(output, firstBreakOutFlag, starttime):
    # print first break out in health check
    if "500" in output and firstBreakOutFlag:
        firstBreakOutFlag = False
        print("first break out in health check  {}".format(datetime.datetime.now() - starttime))
    return firstBreakOutFlag
introduceFault(
                # faultcommand="curl -s -X POST https://travelfusionprovideradapter-service-int.test.air.expedia.com/__fault/cpu",
                # healthcommand="curl -I -X GET https://travelfusionprovideradapter-service-int.test.air.expedia.com/__heartbeat",
                faultcommand="curl -s -X POST https://sabreprovideradapter-aws.us-west-2.test.air.expedia.com/__fault/cpu",
               healthcommand="curl -I -X GET https://sabreprovideradapter-aws.us-west-2.test.air.expedia.com/__heartbeat",
               
               heatlhintervalInSeconds=30, healthyThreshold=3, unhealthyThreshold=10)

# index=app sourcetype="sabre*" logger=com.expedia.heartbeat.core.DefaultDetector