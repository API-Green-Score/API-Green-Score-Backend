#!/usr/bin/env sh

echo "Launching a DUMP of mongo APIGREEENSCORE database ..."

echo "   Computing backup directory and file ..."
BACKUP_DIR=
if [ $# -eq 1 ]; then
  echo "      Using specific backup directory: $1"
  BACKUP_DIR="$1"
else
  echo ""
  echo "ERROR : Please give a backup directory as an argument. Example: /some/path/on/your/host"
  echo ""
  exit 1
fi

CUR_DATE=$(date '+%Y-%m-%d_%H-%M-%S')
BACKUP_FILE=$BACKUP_DIR/apigreenscore_$CUR_DATE.dump_gzip

echo "   Computing backup directory and file ... [ OK ]"

echo "   Executing mongodump command to create file '$BACKUP_FILE' ..."
docker exec mongo-greenscore sh -c 'exec mongodump --gzip -d local --archive' > $BACKUP_DIR/apigreenscore_$CUR_DATE.dump_gzip
echo "   Executing mongodump command to create file '$BACKUP_FILE' ... [ OK ]"

echo "Launching a DUMP of mongo APIGREEENSCORE database ... [ OK ]"