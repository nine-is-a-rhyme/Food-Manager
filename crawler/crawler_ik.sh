#--crawler_ik.sh--#############################################################
#                                                                             #
# This program downloads the rezipes from http://www.ichkoche.at/             #
#                                                                             #
# last change:06.05.2014                                                      #
#                                                                             #
# PROGRAMMED BY LUKAS REISINGER & MATTHIAS EDER                               #
###############################################################################

#! /bin/bash

# VARIABLES
START=$1 #STARTING NUMBER
END=$2 #ENDING NUMBER
PATH1="http://www.ichkoche.at/ribiseltorte-rezept-"
PATH2=""
CSV=ichkoche.json

# COMPILE C PROGRAMM
gcc -o ichkoche getDataIchKoche.c -Wall

# USAGE OUTPUT (IF THERE ARE NO PARAMETERS
if [ "x$END" == "x" ]
  then
    echo "NO PARAMS"
    echo "USAGE: <START NUMBER> <END NUMBER>"
  exit
fi

# START DOWNLOADING
while [ $START -le $END ]
  do
    clear
    echo "ICH KOCHE Rezipe number: $START from $END"
    
    # PATH WHERE THE FILE ARE SAVED
    FILEPATH="files/$START"

    # DOWNLOAD THE REZIPES FROM THE WEBSITE http://www.ichkoche.at/
    wget -q -N -O "$FILEPATH" "$PATH1$START$PATH2"

    # CALL THE EMPTY.SH THE DELETE EMPTY FILES
    ./empty_ik.sh "$FILEPATH"

    #INCREASE THE COUNTER
    START=$(( $START + 1 ))
done

# START THE C PROGRAMM TO WRITE DATA TO CSV
./ichkoche $CSV $END

