# detectprojv2j

### Software detectproj, version 1.3 (01/2026),
Moved from Java 8 to Java 25. Support of the inverse reprojection on selected projections. Reprojection by tiles is thread-safe

<hr />

### detectproj, version 1.2.1 (02/2024),
Minor fixies and speed imrovements.

<hr />

### detectproj, version 1.2(02/2022),
Improved performance of the projection analysis.

<hr />

### detectproj, version 1.1.1 (08/2019),
Support of the inverse equations for the selected map projections (labeled by *). Experimantal support of the inverse reprojection on selected projections. An improved performance of the projection analysis. 10 new map projections have been added.

<hr />

### detectproj, version 1.1 (01/2019),
Automated estimation of the map projection and its parameters based on the non-linear optimization.

<hr />

### detectproj, version 1.09 (07/2017)
Extended identification of the projection. Results sortable according to determined parameters.

<hr />

### detectproj, version 1.08 (05/2017)
Added 3 map projections: Behrmann, Miller, Wiechel. New graticule reconstruction algorithm.

<hr />

### detectproj, version 1.07 (04/2017)
Added panning tool. Some minor fixies and convergence improvements.

<hr />

### detectproj, version 1.06 (03/2017)
OSM issue fixed The Open Street Map loading issue has been fixed.

<hr />

### detectproj, version 1.05 (03/2017)
Added 6 new map projections: Adams (3), Guyou, CWE, Littrow, Peirce. Some minor fixies and improvements.

<hr />

### detectproj, version 1.04 (03/2017)
Setting multiple latitude/longitude interval + sampling step of generated meridians/parallels. Compare old and new versions. Multiple directions of the transformed longitude are supported. Graticule of the projection may be generated over the entitre planishere (not only inside the spherical quadrangle represented by min-max box).

<hr />

### detectproj, version 1.03 (12/2016)
Support λ0 parameter for the oblique aspect of the projection. Compare old and new versions. 

<hr />

### detectproj, version 1.02 (11/2016)
Added drag and drop support. Analyzed map. list of control points opened by drag and drop. 

<hr />
 
### detectproj, version 1.01 (10/2016)
December 2, 2016: 12 new map projections are supported.

<hr />

### detectproj, version 1.00 (9/2016)
First version of the detectproj. Convergence improvements for all detection methods.

<hr />


## Features of the detectproj now include

<li><span style="font-size: 14pt;">100 map projections are supported,</span></li>
<li><span style="font-size: 14pt;">detection of the projection name and family,</span></li>
<li><span style="font-size: 14pt;">estimation of the normal/transverse/oblique aspect of the projection,</span></li>
<li><span style="font-size: 14pt;">detection of true parallels lat_1, lat_2,</span></li>
<li><span style="font-size: 14pt;">detection of the central parallel shift lon_0,</span></li>
<li><span style="font-size: 14pt;">estimation of the map scale, map rotation (optional),</span></li>
<li><span style="font-size: 14pt;">2 detection methods,</span></li>
<li><span style="font-size: 14pt;">3 optimization techniques,</span></li>
<li><span style="font-size: 14pt;">fast detection in the separate thread,</span></li>
<li><span style="font-size: 14pt;">two map windows side by side,</span></li>
<li><span style="font-size: 14pt;">Open Street map client as the reference map,</span></li>
<li><span style="font-size: 14pt;">drag and drop operations,</span></li>
<li><span style="font-size: 14pt;">add/edit/delete control points,</span></li>
<li><span style="font-size: 14pt;">list of candidate projections sorted by the residuals,</span></li>
<li><span style="font-size: 14pt;">visualization of the detected parameters and residuals,</span></li>
<li><span style="font-size: 14pt;">import/export of control points,</span></li>
<li><span style="font-size: 14pt;">export reconstructed graticules in DXF,</span></li>
<li><span style="font-size: 14pt;">setting latitude/longitude interval of the reconstructed graticule,</span></li>
<li><span style="font-size: 14pt;">setting sampling sensitivity of the reconstructed meridians/parallels,</span></li>
<li><span style="font-size: 14pt;">support multiple modes of the transformed longitude direction,</span></li>
<li><span style="font-size: 14pt;">and many more...<br /></span></li>
</ul>
<hr />

## Determined parameters
<p style="text-align: justify;"><span style="font-size: 14pt;">Suppose the projection ℙ(φk, λk, φ1, φ2, λ0, κ) described by the set of constant values: transformed pole position [φk, λk], standard parallels φ1, φ2, longitude λ0 of the central meridian, constant parameter κ (may be assigned to any other determined value). Furthermore, suppose the analyzed map M(R′, ΔX, ΔY, α) described by its constant values: auxiliary sphere radius R′ (illustrating the scale ratio), origin shifts ΔX, ΔY, and angle of rotation α. For the oblique aspect, the projection equations in closed form may be written as functions of the determined parameters</span></p>
<p style="text-align: center;"><span style="font-size: 14pt;">X(R′, φk, λk, φ1, φ2, λ0, ΔX, κ, α) = F(φ′, λ′),<br />Y(R′, φk, λk, φ1, φ2, λ0, ΔY, κ, α) = G(φ′, λ′).</span></p>
<p><strong><span style="font-size: 14pt;">Determined parameters of the projection</span></strong></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">During the analysis, the bellow-mentioned constant parameters of the projection &nbsp;ℙ are determined. They have a strong influence on the shape of the graticule.</span></p>
<ul>
<li><span style="font-size: 14pt;">Transformed pole position [φk, λk]<br />For the normal aspect, the arbitrary pole position is K=[ 90,0 ] is fixed; for the transverse aspect K=[ 0,? ] . Otherwise, both coordinates need to be determined.</span></li>
<li><span style="font-size: 14pt;">Standard parallels φ1, φ2<br />The latitudes of two standard parallels, representing intersections of the cone, cylinder secant plane, are determined.</span></li>
<li><span style="font-size: 14pt;">Longitude λ0 of the central meridian<br />To minimize the distortion and provide a true projection of the mapped region, the central meridian may be shifted. It is frequently chosen in the axis of the symmetry of the mapped region.</span></li>
<li><span style="font-size: 14pt;">Abitrary constant parameter κ<br />It may represent any other constant value of the projection.<br /></span></li>
</ul>
<p><strong><span style="font-size: 14pt;">Determined parameters of the map</span></strong></p>
<p><span style="font-size: 14pt;">The constant parameters of the map represent its scale, shift, and rotation. They have only a low effect on the graticule shape.</span></p>
<ul>
<li><span style="font-size: 14pt;">Scale parameter R'<br />It is determined so as to estimated projection graticule fits best with the analyzed one.</span></li>
<li><span style="font-size: 14pt;">Rotation α<br />An additional rotation of the analyzed map caused by the inappropriate insertion of the paper form of the map into the scanner.<br /></span></li>
</ul>

## Detection methods
<p style="text-align: justify;"><span style="font-size: 14pt;">Determining the best fit projection parameters represents a complex problem leading to the convex/global unconstrained optimization of the objective function φ, describing the similarity of the analyzed and reference maps.</span></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">Let P ∈ M and Q ∈ S2 be the sets of features on the analyzed map M and on the sphere S2, ℙx:S2 → M′ be the analyzed projection, and P′ ∈ M′ be the image of Q in ℙx. The dissimilarity &nbsp;δx, δx ≥ 0,</span></p>
<p style="text-align: center;"><span style="font-size: 14pt;">δx = ϕ (ℙx(Q), P) = ϕ (P′x, P),</span></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">of features on the analyzed map M and on the sphere S2 projected with ℙ, is measured by the objective function ϕ at a point x. For each analyzed map projection ℙ, the vector of its best constant values</span></p>
<p><img src="images/formula.png" alt="formula" width="241" height="51" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">minimizing ϕ, may be determined. The minimized function is represented by the sum of the squares of residuals on the corresponding points</span></p>
<p style="text-align: center;"><span style="font-size: 14pt;">ϕ (P′x, P), = rTr.</span></p>
<p><span style="font-size: 14pt;">Two types analyses are supported:</span></p>
<ul>
<li><span style="font-size: 14pt;">Method M7: 7 determined parameters of the projection (no rotation of the map is supposed).</span></li>
<li><span style="font-size: 14pt;">Method M8: 8 determined parameters, map rotation allowed.</span></li>
</ul>
<p><span style="font-size: 14pt;">The problem leads to the global unconstrained optimization. Three optimization techniques are supported:</span></p>
<ul>
<li><span style="font-size: 14pt;">NLS (Non-linear Least Squares): convex optimization, only local optimizer is guaranteed.</span></li>
<li><span style="font-size: 14pt;">NM (Nelder Mead): non-convex optimization, direct-search method, global optimizer may be found (no guarantee).</span></li>
<li><span style="font-size: 14pt;">DE (Differential Evolution): non-convex optimization, stochastic method, global optimize may be found (no guarantee), best results (slowest)</span></li>
</ul>

## About map projections...
<p style="text-align: justify;"><span style="font-size: 14pt;">Map projections are important for creating maps; each map uses a projection. Map projection (or a mapping) transforms a position of the element on the curved surface into a flat surface (map), represented by the plane. A curved surface approximating the Earth is considered to be the sphere or ellipsoid. Each map projection ℙ is defined with the set coordinate functions F, G of two independent variables φ, λ</span></p>
<p style="text-align: center;"><span style="font-size: 14pt;">X = F(φ, λ), Y = G(φ, λ),</span></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">which are continuous with their first order partial derivatives. The meridian of a longitude λ = λ0 = const is represented by the curve</span></p>
<p style="text-align: center;"><span style="font-size: 14pt;">X = F(φ, λ0), Y = G(φ, λ0),</span></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">and analogously, a parallel of a latitude φ = φ0 = const, is</span></p>
<p style="text-align: center;"><span style="font-size: 14pt;">X = F(φ0, λ), Y = G(φ0, λ).</span></p>
<p><span style="font-size: 14pt;">The map projection analysis represents a challenging, but conceptually difficult, task.</span></p>
<p><span style="font-size: 14pt;">Cylindrical, conic, azimuthal projections:</span></p>
<p><img src="images/proj_aspects.png" alt="proj aspects" width="541" height="256" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<p><strong><span style="font-size: 14pt;">Map projection families</span></strong></p>
<p><span style="font-size: 14pt;">Depending on the shape of the graticule, the &nbsp;are several important families.</span></p>
<ul>
<li style="text-align: justify;"><span style="font-size: 14pt;"><strong>Simple projection</strong><br />A projection surface is represented by the developed surface touching or intersecting a sphere. It is associated with the cylindrical, azimuthal, and conic projections.</span></li>
</ul>
<p><span style="font-size: 14pt;">Cylindrical equal-area projection:<br /><img src="images/eqa.png" alt="eqa" width="793" height="393" style="display: block; margin-left: auto; margin-right: auto;" /><br /></span></p>
<ul>
<li style="text-align: justify;"><span style="font-size: 14pt;"><strong>Pseudo projections</strong><br />Sometimes, a concept of the auxilliary gemetrical solids is not available, but some analogies remains. To avoid shape distortions, several refinements can be found in the inherited families: pseudocylindrical, pseudoconic, pseudoazimuthal.</span></li>
</ul>
<p><span style="font-size: 14pt;">Bonne projection:<br /><img src="images/bonne.png" alt="bonne" width="627" height="576" style="display: block; margin-left: auto; margin-right: auto;" /><br /></span></p>
<ul>
<li><span style="font-size: 14pt;"><strong>Globular projections</strong><br />Showing a hemisphere bounded by a circle, they belong to the oldest-known projections using easily drawn curves.</span></li>
</ul>
<p><span style="font-size: 14pt;">Apianus projection:</span></p>
<p><img src="images/apian.png" alt="apian" width="618" height="618" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<ul>
<li style="text-align: justify;"><span style="font-size: 14pt;"><strong>Perspective projections</strong><br />Some cylindrical or azimuthal projections may be derived using a perspective, when the sphere is projected to a part of the cylinder. &nbsp;The plane may touch the sphere, but a secant forms are also known.</span></li>
</ul>
<p><span style="font-size: 14pt;">Wetch projection:</span></p>
<p><img src="images/wetch.png" alt="wetch" width="637" height="756" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<ul>
<li><span style="font-size: 14pt;"><strong>Polyconic projections</strong><br />The sphere is projected to the plane with infinitely many cones tangent to each parallel. In other words, the map is split into many strips each tangent to a different cone. The typical shape of the polyconic projections, a sliced apple, can easily be recognized.</span></li>
</ul>
<p><span style="font-size: 14pt;">Hassler projection:</span></p>
<p><img src="images/hassler.png" alt="hassler" width="636" height="488" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<ul>
<li><span style="font-size: 14pt;"><strong>Modified azimuthal</strong><br />They are based on a geometric modifications of azimuthal projections, different than for pseudoazimuthal projections. Providing a natural depiction the entire planisphere they are used &nbsp;for worls maps.</span></li>
</ul>
<p><span style="font-size: 14pt;">Aitoff projection:</span></p>
<p><img src="images/aitoff.png" alt="aitoff" width="696" height="349" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<ul>
<li><span style="font-size: 14pt;"><strong>Interrupted projections</strong><br />They are based on idea that highly distorted regions may be moved to less-distorted parts near the equator or a central meridian. The sphere is projected per-partes with a common projection or combining several different projections.</span></li>
</ul>
<p><span style="font-size: 14pt;">Good projection:</span></p>
<p><img src="images/good.png" alt="good" width="712" height="320" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<ul>
<li><span style="font-size: 14pt;"><strong>Miscellaneous projections</strong><br />Many projections can not be classified into the above mentioned group. They are based on the different mathematical/geometrical approaches or have a different shape of meridians/parallels/pole.</span></li>
</ul>
<p><img src="images/armad.png" alt="armad" width="613" height="350" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<p>&nbsp;</p>
<p><strong><span style="font-size: 14pt;">Projection aspect</span></strong></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">Map projections are proposed to represent the entire Earth, a hemisphere, continent, or country as accurately as possible. One of the ways to achieve this is an appropriate choice of the projection aspect. There are three projection aspects, which differ in the position of the pole K = [φk, λk]:</span></p>
<ul>
<li><span style="font-size: 14pt;">Normal aspect<br />The projection pole K = [90○, 0○] coincides with the North Pole of the Earth. In connection with azimuthal projections, it is also known as the polar aspect.</span></li>
<li><span style="font-size: 14pt;">Transverse aspect<br />The projection pole K = [0○, λk], λk ∈ [ − 180○, 180○], coincides with the equator and it is also called the equatorial aspect. There are an infinite number of positions of the transverse aspect.</span></li>
<li><span style="font-size: 14pt;">Oblique aspect<br />The projection pole K = [φk, λk], where φk ∈ [ − 90○, 90○], λk ∈ [ − 180○, 180○], is any other point than the North Pole or the equatorial point.</span></li>
</ul>
<p style="text-align: justify;"><span style="font-size: 14pt;">The projection aspect has a crucial impact on the graticule shape. In both the transverse and oblique aspects, the shape of the graticule significantly changes; other constant values of the projection are gentler to the shape of the graticule. It is a known fact that projections may lose their orthogonality shapes of the poles, prime meridian, or equator shape.</span></p>
<p><img src="images/projections_normal_oblique_small.png" alt="projections normal oblique small" width="683" height="543" style="display: block; margin-left: auto; margin-right: auto;" /></p>
<p style="text-align: justify;"><span style="font-size: 14pt;">Comparing the azimuthal (a), (b) and conic equidistant (c), (d) projections in the normal and oblique, K=[ 50○, 10○] , aspects; the orthogonality and graticule shapes are not preserved.</span></p>
