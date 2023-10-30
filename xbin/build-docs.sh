#!/bin/bash
#
bDATE=$(date '+%Y.%m.%d_%H%M%S')

XDIR=$(cd $(dirname $0) && pwd)

FROM=""
for x in ${XDIR}/../doc/*.md; do

    FROM="$FROM -i ${x}"

    T=$(basename $x)

done

(
cd $XDIR && $XDIR/md2pdf.sh ${FROM} -o $XDIR/../doc/luay-docs.pdf
) >/dev/null 2>/dev/null


# END.