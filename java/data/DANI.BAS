5 ' DANI
7 ' By Sean Davidson
10 '   Introduction
20 '
30 CLS:KEYOFF:KEY1,"keyon"+CHR$(13): PRINT"Dynamic Artificial Non-Intelligence"
40 PRINT:PRINTSPC(l6)"DANI"
50 FOR T=1 TO 4:PRINT:NEXT
60 PRINT"  Greetings human!"
70 PRINT"    This is your MSX speaking."
80 PRINT:PRINT"    Talk to me, and I will"
90 PRINT"   learn from what you say, and"
100 PRINT"  answer you."
110 PRINT"    If you don't think I am"
120 PRINT"  learning anything, type 'list'"
130 PRINT" and I will divulge my knowledge."
140 PRINT:PRINT
150 '
160 '  Setup
170 '
180 CLEAR 5000:DEFINT A-Z
190 MA=500:DIM WD$(MA),FO$(MA,1):LW=0 
200 WD$(0)="@":MW=0
210 INPUT"Would you like our conversation to be recorded on printer ";PR$:PR=(ASC(PR$+"N")=121 OR ASC(PR$+"N")=89)
220 PRINT:PRINT:PRINT
230 PC$="@.!?":LG$="-'abcdefghliklmnopgrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ":LC=28
240 PRINT"Speak now, or forever hold your peace"
300 '
310 '  Input text line
320 '
330 IF POS(9)>0 THEN PRINT:IF PR THEN LPRINT
340 PRINT">";:IF PR THEN LPRINT">"; 
350 PN=PN+1:IF PN=2 THEN AT$="@ "
360 LINE INPUT TX$:IF TX$="list" THEN 940
370 IF TX$="" THEN 1348
380 IF PR THEN LPRINT TX$
390 TX$=AT$+TX$:PS=0:TL=LEN(TX$) 
500 '
510 '  Analyze text Imnp, find words"
520 '
530 WD$=""
540 PS=PS+1:IF PS>TL THEN IF WD$>""THEN 730 ELSE 1340
550 LT$=MID$(TX$,PS,1)
560 IS=INSTR(LG$,LT$)
570 IF IS THEN WD$=WD$+MID$(LG$,IS+26*(IS>LC),1):GOTO540
580 IF LT$=" " THEN IF WD$="" THEN 540 ELSE 730
590 IF INSTR(PC$,LT$)=0 THEN 540
600 IF LT$="?" THEN LT$="."
610 IF WD$="" THEN WD$=LT$:GOTO 730 
620 PS=PS-1
700 '
710 '  Put words in linked array
720 '
730 WD=-1
740 IF WD$="i" THEN WD$="I"
750 WD=WD+1:IF WD>MW THEN WD$(WD)=WD$:MW=MW+1:GOTO 770
760 IF WD$<>WD$(WD)THEN 750
770 HI=WD/220:LO=WD-HI*220
780 LH$=CHR$(LO+32)+CHR$(HI+32) 
790 IS=INSTR(FO$(LW,0),LH$)
800 IF IS=0 THEN FO$(LW,0)=FO$(LW,0)+LH$:FO$(LW,1)=FO$(LW,1)+" ":GOTO 820 
810 MID$(FO$(LW,1),INT(IS/2)+1)=CHR$(ASC(MID$(FO$(LW,1),INT(IS/2)+1))+1) 
820 LW=WD
830 GOTO 530
900 '
910 '  Print results table
920 ' FROM> 360
930 '
940 PRINT:PRINT"Word. Followed by (number of times)":FOR WD=0 TO MW
950 PRINT WD;WD$(WD),
960 FOR L= 1 TO LEN(FO$(WD,0)) STEP 2
970 PR$=LEFT$(MID$(FO$(WD,0),L,2)+" ",2)
980 NM$=LEFT$(MID$(FO$(WD,1),INT(L/2)+1,1)+" ",2)
990 VL$=PR$:GOSUB 1100:V2=VL
1000 VL$=NM$:GOSUB 1100
1010 PRINT V2"("VL")";
1020 NEXT L:PRINT:NEXT WD
1030 PRINT:GOTO 330
1050 '
1060 ' SUB> calc actual value from 
1070 '  string representaion
1080 ' CALLED BY> 560,570
1090 '
1100 VL=ASC(VL$)-32+220*(ASC(RIGHT$(VL$,1))-32):RETURN
1150 '
1160 ' SUB> choose word path
1170 ' CALLED BY> 1030
1180 '
1190 CH$="":FOR R=1 TO LEN(FR$):CH$=CH$+STRING$(ASC(MID$(FR$,R)+"  ")-31,R):NEXT R
1200 WN=ASC(MID$(CH$+CHR$(0),INT(RND(8)*LEN(CH$)+1)))
1210 WC=ASC(MID$(PS$,WN*2-1)+"  ")-32
1220 RETURN
1300 '
1310 ' reply 
1320 '   FROM> 370,540
1330 '
1340 WC=0:W=0:IF PN=1 OR RND(9)>.9 THEN W=RND(8)*MW
1350 PS$=FO$(WC,0):FR$=FO$(WC,1) 
1360 GOSUB 1190:IF WC=0 THEN 330
1370 W=W+1
1380 IF W=1 THEN WP$=CHR$(ASC(WD$(WC))+32*(WD$(WC)<>"I"))+MID$(WD$(WC),2)+"":PRINT WP$;:IF PR THEN LPRINT WP$;:GOTO 1400:ELSE GOTO 1400
1390 PRINT WD$(WC)" ";:IF PR THEN LPRINT WD$(WC)" ";
1400 GOTO 1350
