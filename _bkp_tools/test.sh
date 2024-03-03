#!/usr/bin/env sh

## !/bin/bash
############################################################
# Help                                                     #
############################################################
Help()
{
   # Display Help
   echo "Initialize of the mongo APIGREENSCORE database."
   echo
   echo "Syntax: scriptTemplate [-h|d|s|v]"
   echo "options:"
   echo "h     Display this help message"
   echo "d     Add a specific data directory"
   echo "s     Execute initialization script and erase database content"
   echo "v     Verbose mode"
   echo
}

############################################################
############################################################
# Main program                                             #
############################################################
############################################################

# Set variables
Name="world"
MOUNT_DIR_DATA=
SCRIPT

############################################################
# Process the input options. Add options as needed.        #
############################################################
# Get the options
while getopts ":hn:" option; do
   case $option in
      h) # display Help
         Help
         exit;;
      d) # Add data directory to mount
         MOUNT_DIR_DATA="-v $1:/data/db";;
      s) # Execute initialization script and erase database content

     \?) # Invalid option
         echo "Error: Invalid option"
         exit;;
   esac
done


echo "hello $Name!"