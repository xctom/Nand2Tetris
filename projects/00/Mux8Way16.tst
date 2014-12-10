// This file is part of the materials accompanying the book 
// "The Elements of Computing Systems" by Nisan and Schocken, 
// MIT Press. Book site: www.nand2tetris.org
// File name: projects/00/Mux8Way16.tst

load Mux8Way16.hdl,
output-file Mux8Way16.out,
compare-to Mux8Way16.cmp,
output-list a%X1.4.1 b%X1.4.1 c%X1.4.1 d%X1.4.1 e%X1.4.1 f%X1.4.1 g%X1.4.1 h%X1.4.1 sel%D2.1.2 out%X1.4.1;

set a 0,
set b 0,
set c 0,
set d 0,
set e 0,
set f 0,
set g 0,
set h 0,
set sel 0,
eval,
output;

set sel 1,
eval,
output;

set sel 2,
eval,
output;

set sel 3,
eval,
output;

set sel 4,
eval,
output;

set sel 5,
eval,
output;

set sel 6,
eval,
output;

set sel 7,
eval,
output;

set a %X1234,
set b %X2345,
set c %X3456,
set d %X4567,
set e %X5678,
set f %X6789,
set g %X789a,
set h %X89ab,
set sel 0,
eval,
output;

set sel 1,
eval,
output;

set sel 2,
eval,
output;

set sel 3,
eval,
output;

set sel 4,
eval,
output;

set sel 5,
eval,
output;

set sel 6,
eval,
output;

set sel 7,
eval,
output;
