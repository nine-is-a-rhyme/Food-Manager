#--empty_ik.sh--###############################################################
#                                                                             #
# This program deletes empty files                                            #
#                                                                             #
# last change:06.05.2014                                                      #
#                                                                             #
# PROGRAMMED BY LUKAS REISINGER & MATTHIAS EDER                               #
###############################################################################

#! /bin/bash

# VARIABLES
START=$1
END=$2

# CHECK IF TEHER IS A SECOND PARAMETER
if [ "x$END" == "x" ]
  then

    # CHECK IF THE FILE IS EMPTY
    if ! test -s "$START"
      then
        rm "$START"
    fi
  exit
fi

# IF THERE IS A SECOND PARAMETER DELETE THE FILES FROM START TO END
while [ $START -le $END ]
  do
    
    # CHECK IF THE FILE IS EMPTY
    if ! test -s "../../files/ichkoche/$START"
      then
       rm "../../files/ichkoche/$START"
    fi 

    # INCREASE THE COUNTER
    START=$(( $START + 1 ))
done

exit
