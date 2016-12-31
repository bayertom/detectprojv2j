<<<<<<< HEAD
<<<<<<< HEAD
# detectprojv2j
# detectprojv2j


<h2><strong><strong><span style="font-family: Arial;">Software </span></strong><span style="color: #ff0000; font-family: Arial;">detectproj</span><span style="font-family: Arial;">, version 1.1,<br />GNU/GPL projection analysis software for Windows ® 7/8/8.1/10, GNU/Linux and MacOS.</span></strong></h2>
<p>Automated estimation of the map projection and its parameters based on the non-linear optimization...<br />Designed for cartographers as well as for enthusiasts.<br />Available free of charge.<br /><br /></p>
<p><span style="color: #ff0000;"><strong>New (December 19, 2016): </strong></span><strong>version&nbsp; 1.1 of</strong><span style="color: #ff0000;"><strong> <a href="https://web.natur.cuni.cz/~bayertom/index.php/projection-analysis/installation">detectproj</a></strong></span><strong>.</strong><span style="color: #ff0000;"><strong><br /></strong></span>Support <span style="font-size: 12pt;">lon<sub>0</sub></span> parameter for the oblique aspect of the projection. Compare <a href="https://web.natur.cuni.cz/~bayertom/images/detectproj/detectproj_sinu.png">old</a> and <a href="https://web.natur.cuni.cz/~bayertom/images/detectproj/detectproj_sinu_lon0_support.png">new</a> versions.</p>
<p>December 9, 2016: Added drag and drop support, <br />December 2, 2016: 12 new map <a href="https://web.natur.cuni.cz/~bayertom/index.php/projection-analysis/supported-projections">projections </a>are supported.<br />November 25, 2016: convergence improvements for all detection methods.<br /><br /></p>
<h3><strong>Features od the <em>detectproj</em> now include:</strong></h3>
<ul>
<li>100 map projections are supported,</li>
<li>detection of the projection name and family,</li>
<li>estimation of the normal/transverse/oblique aspect of the projection,</li>
<li>detection of true parallels lat_1, lat_2,</li>
<li>detection of the central parallel shift lon_0,</li>
<li>estimation of the map scale, map rotation (optional),</li>
<li>2 detection methods,</li>
<li>3 optimization techniques,</li>
<li>fast detection in the separate thread,</li>
<li>two map windows side by side,</li>
<li>Open Street map client as the reference map,</li>
<li>drag and drop operations,</li>
<li>add/edit/delete control points,</li>
<li>list of candidate projections sorted by the residuals,</li>
<li>visualization of the detected parameters and residuals,</li>
<li>import/export of control points,</li>
<li>export reconstructed graticules in DXF,</li>
<li>and many more...</li>
</ul>



<h3><strong>Main controls</strong></h3>
<ul>
<li>Left mouse click: add control point.</li>
<li>Right mouse click: delete the control point.</li>
<li>Mouse wheel: Zoom in/out.</li>
<li>Left mose drag: move the control point.</li>
<li>Right mouse drag: dynamic shift of the map.</li>
</ul>
<h3><br />Control points</h3>
<p>The analysis is based on the minimization of the squares of residuals between test points and projected reference points. The control points are collected by the user. <br />A pair of control points contains the control points on the analyzed map and on the reference map.</p>
<p><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_test_point.png" alt="detectproj test point" width="29" height="26" />&nbsp;&nbsp;&nbsp;&nbsp; symbol of the test point on the analyzed map,</p>
<p><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_reference_point.png" alt="detectproj reference point" width="30" height="30" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; symbol of the reference point on Open Street Map.</p>
<p>Control points located on the map items:<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_points_map_small.png" alt="detectproj points map small" width="675" height="300" /></p>
<p>Control points located on the meridian/parallel intersection:</p>
<p>&nbsp;<img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_points_graticule_small.png" alt="detectproj points graticule small" width="668" height="300" /></p>
<p>&nbsp;</p>
<h3 style="text-align: justify;"><strong>Properties of the control points</strong></h3>
<p style="text-align: justify;">The proposed techniques are suitable for sets with approximately the same spatial density of features. On the boundaries of the analyzed region, in particular, it is necessary to place enough points.</p>
<ol style="text-align: justify;">
<li>The uniform distribution of analyzed collected points on a map (grid, random set) is crucial.</li>
<li>The estimated parameters fit well inside the convex hull of the analyzed set; noextrapolation of results outside the analyzed set is supported.</li>
<li>The proper estimation of parameters on the boundary of the map, the analyzed features should be collected at the margins of the map.</li>
<li>For the small-scale maps 5 points are sufficient, for the medium-scale maps 10 points are recommended, but for the large-scale maps 15-20 points may be required.</li>
<li>For early maps, the graticule is significantly more accurate than the map content. Placing control points in the meridian and parallel intersections brings lower residuals between the original and reconstucted graticule.</li>
</ol>
<p style="text-align: justify;">The recommended control point distributions:</p>
<p><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_good_distributions.png" alt="detectproj good distributions" width="640" height="176" /></p>
<p>Not recommended point distributions:</p>
<p><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_bad_distributions.png" alt="detectproj bad distributions" width="640" height="364" /></p>
<p style="text-align: justify;">Our algorithm performs recursive splitting of an edge depending on its geometric complexity and significance. The splitting criterion is calculated for each detected edge of the building. It is based on the calculation of standard deviation ?, that minimizes the sum of the squares of the points distances from the regression line; the variance of the residuals is the minimum possible. The regression line is oriented in accordance with one edge of the smallest area enclosing rectangle, it is parallel to this edge. The regression line also passes through the center of gravity of the set of points.</p>
<p><span style="color: #0000ff;"><strong><span style="font-size: 14pt;">Analyze projection of the early map</span></strong></span></p>
<h3>Step1: Import early map</h3>
<p>The existing early map can be imported from a file. For successfull import, the file can not exceed 50 MB. The following graphic formats are supprted:</p>
<ul>
<li>JPG - Joint Photographic Expert Group,</li>
<li>PNG - Portable Network Graphics,</li>
<li>GIF - Graphics Interchange program.</li>
</ul>
<p>To import early map file do the following steps:</p>
<ol>
<li>Click <strong>File</strong> in the menu, and select the <strong>Import map </strong>item. Alternately, click on the icon.</li>
<li>In the dialog window <strong>Upload early map</strong> select the graphic file.</li>
</ol>


<p class="MsoNormal"><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">A pair of control points is represented by the control point on the analyzed map and the control point on the reference map.<span style="mso-spacerun: yes;">&nbsp; </span></span><span class="alt-edited"><span lang="EN" style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-ansi-language: EN;">Regardless</span></span><span class="shorttext"><span lang="EN" style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-ansi-language: EN;"> the order in which control points are collected (analyzed map first and reference map second or vice versa) a pair will be added to the list.</span></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span></p>
<ol>
<li><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">Using mouse wheel or <strong style="mso-bidi-font-weight: normal;">Zoom in</strong> tool </span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-fareast-language: CS; mso-no-proof: yes;"> </span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"><span style="mso-spacerun: yes;"></span>zoom the map and find a feature on the map suitable for the analysis. Check its depiction on the reference map.</span></li>
<li><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; font-family: 'Times New Roman',serif; mso-fareast-font-family: 'Times New Roman';"><span style="mso-list: Ignore;"><span style="font: 7.0pt 'Times New Roman';"> </span></span></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">In the toolbar, left-click on the <strong style="mso-bidi-font-weight: normal;">Add control points</strong> tool </span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-fareast-language: CS; mso-no-proof: yes;"> </span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"><span style="mso-spacerun: yes;"></span>.</span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; font-family: 'Times New Roman',serif; mso-fareast-font-family: 'Times New Roman';"><span style="mso-list: Ignore;"><span style="font: 7.0pt 'Times New Roman';"></span></span></span></li>
<li><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">Move the cursor above the desired control point and press the left mouse button. Above the early map, the red control point mark. Analogously, the yellow control point mark appears.</span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"><br /></span><br /><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_control_points_small.png" alt="detectproj control points small" width="644" height="353" /><br /><br /></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-fareast-language: CS; mso-no-proof: yes;"></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span></li>
<li><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; font-family: 'Times New Roman',serif; mso-fareast-font-family: 'Times New Roman';"><span style="mso-list: Ignore;"><span style="font: 7.0pt 'Times New Roman';">&nbsp; </span></span></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">Move the cursor above the corresponding point on the second map and press the left mouse button. The control point mark appears<br /><br /></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-fareast-language: CS; mso-no-proof: yes;"></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_control_points_small2.png" alt="detectproj control points small2" width="643" height="352" /><br /><br /></span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span></li>
<li><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">Repeat these steps until all control points are collected.<br /></span><br /><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_control_points_small_all.png" alt="detectproj control points small all" width="851" height="407" /></span></li>
</ol>
<p><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span></p>
<h3 class="MsoListParagraphCxSpFirst" style="margin-left: 21.3pt; mso-add-space: auto; text-indent: -18.35pt; mso-list: l0 level4 lfo1;">Move control points</h3>
<ol>
<li><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">Move the cursor above the desired control point, the pair of control points highlights. On the reference map, its feographic coordinates are displayed.<br /><br /></span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"> </span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-fareast-language: CS; mso-no-proof: yes;"><img src="https://web.natur.cuni.cz/~bayertom/images/control_points_move_small.png" alt="control points move small" width="640" height="353" /></span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">&nbsp; <br /><br /></span></li>
<li><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; font-family: 'Times New Roman',serif; mso-fareast-font-family: 'Times New Roman';"><span style="mso-list: Ignore;"></span></span><span style="mso-bidi-font-size: 12.0pt; line-height: 107%; mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;">Dragging the mouse move the point to a destination position.<br /><br /></span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"><img src="https://web.natur.cuni.cz/~bayertom/images/control_points_move_small2.jpg" alt="control points move small2" width="640" height="352" /><br /></span></li>
</ol>
<p class="MsoNormal" style="text-indent: 21.3pt;"><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin; mso-fareast-language: CS; mso-no-proof: yes;"> </span><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span></p>
<h3>Step 3: Selection of the detection method</h3>
<p>Before analysis it is necessary to select the detection method.</p>
<ol>
<li>From the Combo Box select the desired the detection method.<br /><br /> <img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_methods.png" alt="detectproj methods" /><br /><br /> Alternately, click in the <strong>Analysis</strong> menu, and choose the detection method.<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_methods2.png" alt="detectproj methods2" width="347" height="67" /><br /><br /></li>
<li>Click in selected item, the highlighted method is checked and set as active.<br /><br /></li>
</ol>
<p>M7 is recommended for the unrotated maps; this option is suitable for most situations. M8 achieves the best&nbsp; results for the rotated maps (a map rotation ? is involved). This is typical for situations, when the map is incorrectly placed into the scanner or has a switched orientation on the page. Although M8 brings lower residuals, some solutions might be artificial and represent only a geometric construct not&nbsp; used by the cartographer.</p>
<p class="MsoListParagraph" style="margin-left: 21.3pt; mso-add-space: auto;"><span style="mso-bidi-font-family: Calibri; mso-bidi-theme-font: minor-latin;"></span></p>
<h3>Step 4: Selection of the optimization technique</h3>
<p style="text-align: justify;">Subsequently, the optimization technique needs to be chosen.</p>
<ol>
<li>From the Combo Box select the desired the optimization technique. <br /><br /> <img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_techniques.png" alt="detectproj techniques" /><br /><br /> Alternately, click in the <strong>Analysis</strong> menu, and choose the optimization technique.'<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_techniques2.png" alt="detectproj techniques2" width="317" height="94" /><br /><br /></li>
<li>Click in selected item, the highlighted technique is checked and set as active.</li>
</ol>
<p style="text-align: justify;">The NLS method represents the convex optimization technique which does not ensure the global minimum; only local minimum is found. <span class="shorttext">The direct search technique, Nelder-Mead optimization, belongs to the global optimization methods. Due to small population, the global minimum may not be found. However, it is the fastest method. The differential evolution is the most efficient implemented technique performing a deep exploration of the search space. Unfortunately, it is also the slowest technique.</span></p>
<h3>Step 5: Run analysis</h3>
<p>Recall that at least five pairs of control points need to collected. Due to the time reason, do not use more than 30 control points. There are also several above mentioned recommendations on the control points that must be respected.</p>
<ol>
<li>Click on the&nbsp;<img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_toolbar_analyze_map.png" alt="detectproj toolbar analyze map" /> button. Alternately, click in the Analysis&nbsp; menu, and choose the Analyze map item.</li>
<li>Depending on the amount of analyzed points, selected detection method and optimization technique, the results are evaluated within 10 seconds.<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_small.png" alt="detectproj results small" width="843" height="506" /></li>
</ol>
<p>&nbsp;</p>
<h3>Step 6: Browse results</h3>
<p><span class="shorttext">Let's look more closely at the results provided by the detectproj software.</span></p>
<p><strong>Residuals. </strong>The residuals between the test and projected points can be found in the form <strong>List of control points</strong> form. The possible incorrectly placed control points can be found, moved, or rejected.</p>
<p><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_points_best_fit.png" alt="detectproj results points best fit" width="800" height="146" /></p>
<p><span class="shorttext"><strong>Reconstructed graticule. </strong></span><span class="shorttext">The reconstructed graticule is drawn over the analyzed. Depending on the early map, amount of control points, their accuracy, it provides a good fit. Ideally, the positional or shape discrepancies between the early map and reconstructed graticule should be less than the graphical accuracy of the map (0.1 mm) However, there might be some situations, when the results are not great.</span></p>
<p>&nbsp;<img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_graticule_small.png" alt="detectproj results graticule small" width="670" height="551" /></p>
<p><strong>Projected reference points</strong>. The reference points, drawn with the yellow map markers, projected using the best fit projection, are drawn over the analyzed map. In most situation there is a good fit between the test and projected reference points.<br /><br /></p>
<p><strong>Vector of residuals.</strong> The vetors of residuals between the test and projected reference points are drawn over the analyzed map. Currently, they are visible only under magnification which indicates a good fit.</p>
<p>&nbsp;<img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_residuals.png" alt="detectproj results residuals" /></p>
<h4>Table list of List of detected projections and properties.</h4>
<p>The table summarize results of 20 best-fit projections, their determined parameters as well as the parameters of the analyzed map.</p>
<p>&nbsp;<img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_browse_results.png" alt="detectproj browse results" width="995" height="432" /></p>
<h4>Display results of different projections</h4>
<p>After completing the analysis 20 candidate projections and their parametersare summarized in the table <strong>List of detected projections and properties</strong>. Currently, the resonstructed graticule, projected points and vector of residuals refer to the best-fit projection. To change the active projection do the following steps</p>
<ol>
<li>Click on the&nbsp;<img src="https://web.natur.cuni.cz/~bayertom/images/table.png" alt="table" />&nbsp; button. Alternately, click in the <strong>Analysis&nbsp;</strong> menu, and choose the <strong>Show results</strong> item.<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_projections_best_fit.png" alt="detectproj results projections best fit" width="821" height="148" /><br /><br /></li>
<li>Find the desired projection in the table <strong>List of detected projections and properties</strong>.</li>
<li>In the table, click on the row and make the projection active; the table row which higlihts.</li>
</ol>
<p style="margin-left: 30px;"><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_gall_fit.png" alt="detectproj results gall fit" width="820" height="145" /><br /> <br /> Subsequently, the reconstructed graticule, projected points and residuals refer to the active projection.</p>
<p style="margin-left: 30px;">The differencies beteen candidate projections are not crucical. Sometimes, the possitional differencies are less than thegraphical accuracy of the map. For example, see the results between the Bonne projection (4th position, normal aspect)<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_bonne_graticule_small.png" alt="detectproj results bonne graticule small" width="870" height="667" /></p>
<p style="margin-left: 30px;">and Gall projection (oblique aspect, 18th position).<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_results_gall_graticule_small.png" alt="detectproj results gall graticule small" width="870" height="663" /><br /><br />Unlike the Bonne projection with the curved meridians, the Gall projection uses straight parallels.</p>
<p>&nbsp;</p>
<h3>&nbsp;Step 7: Export reconstructed graticule</h3>
<p style="margin-bottom: 0.0001pt;">The reconstructed graticule, test, and reference projected points can be extracted to the DXF file. It can be processed by CAD or GIS software. Any candidate projection from the <strong>List of detected projections and properties </strong> may be used to project the reference points and to create a graticule.</p>
<ol>
<li>Click on the click in the <strong>Analysis</strong> menu, and choose the <strong>Show results</strong> item.</li>
<li><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">In the table, click on the row and make the projection active; the table row is higlighted<br /><br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_export_graticule_bonne.png" alt="detectproj export graticule bonne" width="821" height="161" /><br /><br /></span></li>
<li>Click in the <strong>Map</strong> menu, and select the <strong>Export graticule </strong>item. Alternately, click on the icon.</li>
<li>In the dialog window <strong>Export test points</strong> select the output DXF file. <br /> <br /><img src="https://web.natur.cuni.cz/~bayertom/images/detectproj_export_graticule_dialog.png" alt="detectproj export graticule dialog" width="484" height="358" /><br /><br /></li>
<li>Click on the <strong>Save</strong> button proceeds the export.</li>
</ol>




<h3>&nbsp;Map 1: Map of Europe, normal aspect of the projection</h3>
<table style="width: 655px; height: 204px; margin-right: auto; margin-left: auto;">
<tbody>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Title:</span></td>
<td><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;L'Europe sous l'Empire de Charlemagne ou tableau historique de cette partie du monde</span></span></span><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;"> </span> </span></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Author: </span></td>
<td><span style="font-size: 12pt; font-family: 'Times New Roman',serif;">&nbsp;Adrien Huber</span></td>
</tr>
<tr>
<td><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;Date:</span></td>
<td><span style="font-family: times new roman,times;">&nbsp; <span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">1828,</span></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Publisher:&nbsp; </span></td>
<td><span style="font-family: times new roman,times;">&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">A. Brue,</span></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Location: </span></td>
<td><span style="font-size: 12pt; font-family: 'Times New Roman',serif;">&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Paris, </span></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Type:</span></td>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Atlas Map, </span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Height: </span></td>
<td><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;37 cm, </span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Width: </span></td>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">51 cm, </span></td>
</tr>
<tr>
<td><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;Scale: </span></td>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">1 : 12,500,000.</span></td>
</tr>
</tbody>
</table>
<p><span style="font-size: 12pt;"><br /><img src="https://web.natur.cuni.c/~bayertom/images/europe_1_bone_grat_2.jpg" alt="europe 1 bone grat 2" width="885" height="636" style="display: block; margin-left: auto; margin-right: auto;" /></span></p>
<p><span style="font-size: 12pt;">Determined parameters of the projection:<br /></span><span style="font-size: 12pt;"><span style="font-size: 12pt;">Projection: Bonne.<br /></span>Transformed pole position: ?<sub>k</sub> = 90.0<sup>0</sup>, ?<sub>k</sub> = 0.0<sup>0</sup>.</span><br /><span style="font-size: 12pt;"> Standard parallels: ?<sub>1</sub> =53. 5<sup>0</sup>, ?2 =53. 5<sup>0</sup>.</span><br /><span style="font-size: 12pt;"> Longitude of the central meridian: ?<sub>0</sub> = 14.7<sup>0</sup>.</span><br /><span style="font-size: 12pt;"> Arbitrary constant parameter: k = 1.0. </span><br /><span style="font-size: 12pt;"> Auxiliary sphere radius: R'= 0.3m. </span><br /><span style="font-size: 12pt;"> Map scale: S = 20 910 248.</span><br /><span style="font-size: 12pt;"> Angle of rotation: ? = 0.00.</span></p>
<p>&nbsp;</p>
<h3><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Map 2: World map in hemisphere, transverse aspect of the projection<br /></span></h3>
<table style="width: 655px; height: 204px; margin-right: auto; margin-left: auto;">
<tbody>
<tr>
<td>&nbsp; <span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Title:</span></td>
<td><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Novus Orbis Sive America Meridionalis Et Septentrionalis : divisa per sua regna, provincias et insul, cura et opera</span></span></span></span><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;"> </span> </span></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Author: </span></td>
<td><span style="font-size: 12pt; font-family: 'Times New Roman',serif;">&nbsp; <span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Seutter, Matthäus, </span><br /></span></td>
</tr>
<tr>
<td><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;Date:</span></td>
<td><span style="font-family: times new roman,times;">&nbsp; <span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">1744</span></span></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Publisher:&nbsp; </span></td>
<td><span style="font-family: times new roman,times;">&nbsp; <span style="font-size: 12pt; font-family: 'Times New Roman',serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Seutter, Matthäus, </span></span><br /></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Location: </span></td>
<td><span style="font-size: 12pt; font-family: 'Times New Roman',serif;">&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Augsburg</span>, </span></span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Type:</span></td>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Atlas Map, </span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Height: </span></td>
<td><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;58 cm, </span></td>
</tr>
<tr>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">Width: </span></td>
<td><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;"><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;50 cm,</span></span></td>
</tr>
<tr>
<td><span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">&nbsp;Scale: </span></td>
<td>&nbsp;<span style="font-size: 12pt; line-height: 107%; font-family: 'Calibri',sans-serif;">1 : 19,000,000</span></td>
</tr>
</tbody>
</table>





=======
"# detectprojv2j" 
>>>>>>> 7fbc3b2cdda953b4c44b50f3080c0e02994e92b8
=======
"# detectprojv2j" 
>>>>>>> 7fbc3b2cdda953b4c44b50f3080c0e02994e92b8
"# detectprojv2j" 
