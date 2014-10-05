*************************************************************************
* IR Models(Vector Space,BM25,DFR/PL2), Query Processing and Evaluation *
*************************************************************************

Objective:
==========
The goal of this project is to gain some experience with different ranking 
algorithms and the evaluation of their performance. 
As part of this project, I needed to build my own
query-processing module in an attempt to boost performance. 

Requirements:
=============
Section 1: Detailed execution steps of baseline code (screenshots and textual
explanations required).
Section 2: Query process implementation (design diagram and result screenshots
required).
Section 3: A thorough analysis and explanations of the baseline comparison and approach.

Part 1 – Evaluation of IR Models:
================================
evaluate the following models:
• Vector Space
• BM25
• DFR/PL2

Part 2 - Query Processing (QP) Module:
======================================
I had to use ‘title-queries.301-450’ as the query file, and the filename is 
self-explanatory. Note that this query file uses only the titles as queries. 
However, the TREC data contains much more information. 

A TREC query/topic has three parts: <title>, <desc> and <narr>.

	<top>
	<num> Number: 301
	<title> International Organized Crime
	<desc> Description:
	Identify organizations that participate in international criminal
	activity, the activity, and, if possible, collaborating organizations
	and the countries involved.
	<narr> Narrative:
	A relevant document must as a minimum identify the organization and the
	type of illegal activity (e.g., Columbian cartel exporting cocaine).
	Vague references to international drug trade without identification of
	the organization(s) involved would not be relevant.
	</top>

And this file is in the same folder with name -- ‘topics.301-450’.
Now task was to design a query-processing module that leverages more of the TREC topic (i.e.
description, narrative) in an attempt to improve retrieval performance.

The query processing must be automated: in other words, you cannot manually
generate a new query file! you can use Java or any scripting language to automatically
process the TREC topics file and generate a new query file.

Requirement: 
============
(i) Document the query-processing algorithm you implemented, 
(ii) Include a listing of the QP code, and 
(iii)Show the actual results.

Part 3 - Analysis of QP Results:
================================
1) Which model performed best overall?
2) Why did one query expansion technique work better than others?
3) What other techniques could you try, given more time?
