#!/bin/sh

export LC_ALL=ja_JP.utf-8
LOGFILE=/tmp/posttepco.log

cd $(dirname $0)
ant posttepco > $LOGFILE

# send log only when the updating was occured.
if grep -q "FAILED" $LOGFILE || grep -q "Error" $LOGFILE; then
	cat $LOGFILE
fi
