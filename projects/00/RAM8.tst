// This file is part of the materials accompanying the book 
// "The Elements of Computing Systems" by Nisan and Schocken, 
// MIT Press. Book site: www.nand2tetris.org
// File name: projects/00/RAM8.tst

load RAM8.hdl,
output-file RAM8.output,
compare-to RAM8.cmp,
output-list time%S1.4.1 in%D1.6.1 load%B2.1.2 address%D3.1.3 out%D1.6.1;

set in 0,
set load 0,
set address 0,
tick,
output;
tock,
output;

set load 1,
tick,
output;
tock,
output;

set in 11111,
set load 0,
tick,
output;
tock,
output;

set load 1,
set address 1,
tick,
output;
tock,
output;

set load 0,
set address 0,
tick,
output;
tock,
output;

set in 3333,
set address 3,
tick,
output;
tock,
output;

set load 1,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 1,
eval,
output;

set in 7777,
tick,
output;
tock,
output;

set load 1,
set address 7,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 3,
eval,
output;

set address 7,
eval,
output;
