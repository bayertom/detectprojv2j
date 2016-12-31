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







=======
"# detectprojv2j" 
>>>>>>> 7fbc3b2cdda953b4c44b50f3080c0e02994e92b8
=======
"# detectprojv2j" 
>>>>>>> 7fbc3b2cdda953b4c44b50f3080c0e02994e92b8
"# detectprojv2j" 
