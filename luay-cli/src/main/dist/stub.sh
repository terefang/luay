#!/bin/sh
MYSELF=`which "$0" 2>/dev/null`
[ $? -gt 0 -a -f "$0" ] && MYSELF="./$0"
MYDIR=$(cd $(dirname $MYSELF) && pwd)

for x in 21/ 17/ 11/ "/"; do
  if test ! -n "$JAVA_HOME"; then
      if test -d "$MYDIR/java${x}"; then
          export JAVA_HOME="$MYDIR/java${x}"
      elif test -d "$MYDIR/jre${x}"; then
          export JAVA_HOME="$MYDIR/jre${x}"
      elif test -d "$MYDIR/../java${x}"; then
          export JAVA_HOME="$MYDIR/../java${x}"
      elif test -d "$MYDIR/../jre${x}"; then
          export JAVA_HOME="$MYDIR/../jre${x}"
      fi
  fi
done

java=java
if test -n "$JAVA_HOME"; then
    java="$JAVA_HOME/bin/java"
fi

LOPTS=" "

export LUAY_HOME="$MYDIR/libluay"

exec "$java" $_JAVA_ARGS -jar $MYSELF ${LOPTS} "$@"

exit 1
