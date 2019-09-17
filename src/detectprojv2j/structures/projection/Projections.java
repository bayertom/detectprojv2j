// Description: List of all implemented map projections
// Supported equations in the non-closed form,

// Copyright (c) 2015 - 2017
// Tomas Bayer
// Charles University in Prague, Faculty of Science
// bayertom@natur.cuni.cz

// This library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this library. If not, see <http://www.gnu.org/licenses/>.

package detectprojv2j.structures.projection;

import static java.lang.Math.*;
import java.util.List;

import detectprojv2j.types.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.exceptions.*;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.newtonraphson.FILat;
import detectprojv2j.algorithms.newtonraphson.FILatDer;
import detectprojv2j.algorithms.newtonraphson.FILon;
import detectprojv2j.algorithms.newtonraphson.FILonDer;
import detectprojv2j.algorithms.newtonraphson.FTheta;
import detectprojv2j.algorithms.newtonraphson.FThetaDer;
import detectprojv2j.algorithms.newtonraphson.NewtonRaphson;
import detectprojv2j.algorithms.numintegration.NumIntegration;
import detectprojv2j.algorithms.quartic.Quartic;
import java.util.ArrayList;


public class Projections {
        
        public static void init (final List <Projection> projections, final TTransformedLongitudeDirection default_lon_dir)
        {
                //Initialize all supported projections and add them to the list
                ProjectionMiscellaneous adamh = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_adamh, Projections::G_adamh, Projections::F_adamh, Projections::G_adamh, "Adams, hemisphere in square", "adamh");
                ProjectionMiscellaneous adams1 = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_adams1, Projections::G_adams1, Projections::F_adams1, Projections::G_adams1, "Adams, world in square I.", "adams1");
                ProjectionMiscellaneous adams2 = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_adams2, Projections::G_adams2, Projections::F_adams2, Projections::G_adams2, "Adams, world in square II.", "adams2");
                ProjectionConic aea = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_aea, Projections::G_aea, Projections::FI_aea, Projections::GI_aea, "Albers, equal area*", "aea");
                ProjectionAzimuthal aeqd = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_aeqd, Projections::G_aeqd, Projections::FI_aeqd, Projections::GI_aeqd, "Equidistant*",  "aeqd");
                ProjectionPseudoAzimuthal aitoff = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_aitoff, Projections::G_aitoff, Projections::FI_aitoff, Projections::GI_aitoff, "Aitoff*", "aitoff");
                ProjectionMiscellaneous api = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_api, Projections::G_api, Projections::F_api, Projections::G_api, "Apianus", "api");
                ProjectionMiscellaneous apiel = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_apiel, Projections::G_apiel, Projections::FI_apiel, Projections::GI_apiel, "Apianus, elliptic*",  "apiel");
                ProjectionMiscellaneous armad = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_armad, Projections::G_armad, Projections::FI_armad, Projections::GI_armad, "Armadillo*", "armad");
                ProjectionMiscellaneous august = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_august, Projections::G_august, Projections::FI_august, Projections::GI_august, "August, epicycloidal*", "august");
                
                ProjectionMiscellaneous bacon = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_bacon, Projections::G_bacon, Projections::F_bacon, Projections::G_bacon, "Bacon, globular", "bacon");
                ProjectionCylindrical behr = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_behr, Projections::G_behr, Projections::FI_behr, Projections::GI_behr, "Behrmann*", "behr");
                ProjectionPseudoCylindrical boggs = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_boggs, Projections::G_boggs, Projections::FI_boggs, Projections::GI_boggs, "Boggs, eumorphic*", "boggs");
                ProjectionPseudoConic bonne = new ProjectionPseudoConic (R0, 90.0, 0.0, 40.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_bonne, Projections::G_bonne, Projections::FI_bonne, Projections::GI_bonne, "Bonne*", "bonne");
                ProjectionAzimuthal breus = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_breus, Projections::G_breus, Projections::F_breus, Projections::G_breus, "Breusign", "breus");
                ProjectionPseudoCylindrical bries = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_bries, Projections::G_bries, Projections::FI_bries, Projections::GI_bries, "Briesenmeister*", "bries");
                ProjectionCylindrical cc = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_cc, Projections::G_cc, Projections::FI_cc, Projections::GI_cc, "Central, perspective*", "cc");
                ProjectionCylindrical cea = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_cea, Projections::G_cea, Projections::FI_cea, Projections::GI_cea, "Lambert, equal area*", "cea");
                ProjectionAzimuthal clar = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_clar, Projections::G_clar, Projections::FI_clar, Projections::GI_clar, "Clark, perspective*", "clar");
                ProjectionMiscellaneous collg = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_collg, Projections::G_collg, Projections::FI_collg, Projections::GI_collg, "Collignon*", "collg");
               
                ProjectionMiscellaneous crast = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_crast, Projections::G_crast, Projections::FI_crast, Projections::GI_crast, "Craster, parabolic*",  "crast");
                ProjectionMiscellaneous cwe = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_cwe, Projections::G_cwe, Projections::F_cwe, Projections::G_cwe, "Conformal world in ellipse", "cwe");
                ProjectionPseudoCylindrical denoy = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_denoy, Projections::G_denoy, Projections::FI_denoy, Projections::GI_denoy, "Denoyer, semi-elliptical*", "denoy");
                ProjectionPseudoCylindrical eck1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eck1, Projections::G_eck1, Projections::FI_eck1, Projections::GI_eck1, "Eckert I.*", "eck1");
                ProjectionPseudoCylindrical eck2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eck2, Projections::G_eck2, Projections::FI_eck2, Projections::GI_eck2, "Eckert II.*", "eck2");
                ProjectionPseudoCylindrical eck3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eck3, Projections::G_eck3, Projections::FI_eck3, Projections::GI_eck3, "Eckert III.*", "eck3");
                ProjectionPseudoCylindrical eck4 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eck4, Projections::G_eck4, Projections::FI_eck4, Projections::GI_eck4, "Eckert IV.*", "eck4");
                ProjectionPseudoCylindrical eck5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eck5, Projections::G_eck5, Projections::FI_eck5, Projections::GI_eck5, "Eckert V.*", "eck5");
                ProjectionPseudoCylindrical eck6 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eck6, Projections::G_eck6, Projections::FI_eck6, Projections::GI_eck6, "Eckert VI.*", "eck6");
                ProjectionPseudoCylindrical eckgr = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eckgr, Projections::G_eckgr, Projections::FI_eckgr, Projections::GI_eckgr, "Eckert-Greifendorff*", "eckgr");
                
                ProjectionMiscellaneous eisen = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eisen, Projections::G_eisen, Projections::F_eisen, Projections::G_eisen, "Eisenlohr", "eisen");
                ProjectionCylindrical eqc = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eqc, Projections::G_eqc, Projections::FI_eqc, Projections::GI_eqc, "Equidistant*", "eqc");
                ProjectionConic eqdc = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eqdc, Projections::G_eqdc, Projections::FI_eqdc, Projections::GI_eqdc, "Equidistant (true parallel lat1)*", "eqdc"); 
                ProjectionConic eqdc2 = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eqdc2, Projections::G_eqdc2, Projections::FI_eqdc2, Projections::GI_eqdc2, "Equidistant (true parallel lat1, pole = point)*", "eqdc2");
                ProjectionConic eqdc3 = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_eqdc3, Projections::G_eqdc3, Projections::FI_eqdc3, Projections::GI_eqdc3, "Equidistant (true parallels lat1, lat2)*", "eqdc3");
                ProjectionPseudoCylindrical fahey = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_fahey, Projections::G_fahey, Projections::FI_fahey, Projections::GI_fahey, "Fahey*", "fahey");
                ProjectionPseudoCylindrical fouc = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_fouc, Projections::G_fouc, Projections::FI_fouc, Projections::GI_fouc, "Foucaut, sine-tangent*",  "fouc");
                ProjectionPseudoCylindrical fouc_s = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_fouc_s, Projections::G_fouc_s, Projections::FI_fouc_s, Projections::GI_fouc_s, "Foucaut, sinusoidal*", "fouc_s");
                ProjectionMiscellaneous fourn = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_fourn, Projections::G_fourn, Projections::F_fourn, Projections::G_fourn, "Fournier I., globular", "fourn");
                ProjectionMiscellaneous fourn2 = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_fourn2, Projections::G_fourn2, Projections::F_fourn2, Projections::G_fourn2, "Fournier II., elliptical", "fourn2");
                
                ProjectionCylindrical gall = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_gall, Projections::G_gall, Projections::FI_gall, Projections::GI_gall, "Gall, stereographic*", "gall");
                ProjectionPolyConic gins4 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_gins4, Projections::G_gins4, Projections::FI_gins8, Projections::GI_gins8, "Ginsburg IV.", "gins4");
                ProjectionPolyConic gins5 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_gins5, Projections::G_gins5, Projections::FI_gins8, Projections::GI_gins8, "Ginsburg V.", "gins5");
                ProjectionPolyConic gins6 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_gins6, Projections::G_gins6, Projections::FI_gins8, Projections::GI_gins8, "Ginsburg VI.", "gins6");
                ProjectionPolyConic gins9 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_gins9, Projections::G_gins9, Projections::FI_gins8, Projections::GI_gins8, "Ginsburg IX.", "gins9");
                ProjectionPolyConic gins8 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_gins8, Projections::G_gins8, Projections::FI_gins8, Projections::GI_gins8, "Ginsburg VIII.*", "gins8");
                ProjectionAzimuthal gnom = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_gnom, Projections::G_gnom, Projections::FI_gnom, Projections::GI_gnom, "Gnomonic*", "gnom");
                ProjectionPseudoCylindrical goode = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_goode, Projections::G_goode, Projections::F_goode, Projections::G_goode, "Goode, homolosine", "goode");
                ProjectionMiscellaneous guyou = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_guyou, Projections::G_guyou, Projections::F_guyou, Projections::G_guyou, "Guyou", "guyou");
                ProjectionPseudoAzimuthal hammer = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_hammer, Projections::G_hammer, Projections::FI_hammer, Projections::GI_hammer, "Hammer*", "hammer");
                
                ProjectionPseudoCylindrical hataea = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_hataea, Projections::G_hataea, Projections::FI_hataea, Projections::GI_hataea, "Hatano, asymmetrical, equal area*", "hataea");
                ProjectionAzimuthal hire = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_hire, Projections::G_hire, Projections::FI_hire, Projections::GI_hire, "La Hire*", "hire");                
                ProjectionAzimuthal jam = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_jam, Projections::G_jam, Projections::FI_jam, Projections::GI_jam, "James, perspective*", "jam");
                ProjectionPseudoCylindrical kav5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_kav5, Projections::G_kav5, Projections::FI_kav5, Projections::GI_kav5, "Kavrayskiy V.*", "kav5");
                ProjectionPseudoCylindrical kav7 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_kav7, Projections::G_kav7, Projections::FI_kav7, Projections::GI_kav7, "Kavrayskiy VII.*", "kav7");
                ProjectionAzimuthal laea = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_laea, Projections::G_laea, Projections::FI_laea, Projections::GI_laea, "Lambert, equal area*", "laea");
                ProjectionMiscellaneous lagrng = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_lagrng, Projections::G_lagrng, Projections::FI_lagrng, Projections::GI_lagrng, "Lagrange, conformal*", "lagrng");
                ProjectionMiscellaneous larr = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_larr, Projections::G_larr, Projections::FI_larr, Projections::GI_larr, "Larrivee*", "larr");
                ProjectionPseudoCylindrical lask = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_lask, Projections::G_lask, Projections::FI_lask, Projections::GI_lask, "Laskowski*", "lask");
                ProjectionConic lcc = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_lcc, Projections::G_lcc, Projections::FI_lcc, Projections::GI_lcc, "Lambert, conformal*", "lcc");
                
                ProjectionConic leac = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_leac, Projections::G_leac, Projections::FI_leac, Projections::GI_leac, "Lambert, equal area (standard parallel lat1)*", "leac");
                ProjectionConic leac2 = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_leac2, Projections::G_leac2, Projections::FI_leac2, Projections::GI_leac2, "Lambert, equal area (standard parallel lat1, pole = point)*", "leac2");
                ProjectionMiscellaneous litt = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_litt, Projections::G_litt, Projections::FI_litt, Projections::GI_litt, "Littrow*", "litt");
                ProjectionPseudoCylindrical loxim = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_loxim, Projections::G_loxim, Projections::FI_loxim, Projections::GI_loxim, "Loximuthal*", "loxim");
                ProjectionPseudoCylindrical maurer = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_maurer, Projections::G_maurer, Projections::FI_maurer, Projections::GI_maurer, "Maurer*", "maurer");
                ProjectionPseudoCylindrical mbt_s = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_mbt_s, Projections::G_mbt_s, Projections::FI_mbt_s, Projections::GI_mbt_s, "McBryde-Thomas, sine I.*", "mbt_s");
                ProjectionPseudoCylindrical mbt_s3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_mbt_s3, Projections::G_mbt_s3, Projections::F_mbt_s3, Projections::G_mbt_s3, "McBryde-Thomas, flat-pole sine III.", "mbt_s3");
                ProjectionPseudoCylindrical mbtfpp = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_mbtfpq, Projections::G_mbtfpq, Projections::FI_mbtfpp, Projections::GI_mbtfpp, "McBryde-Thomas, flat-pole parabolic*", "mbtfpp");
                ProjectionPseudoCylindrical mbtfpq = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_mbtfpq, Projections::G_mbtfpq, Projections::FI_mbtfpq, Projections::GI_mbtfpq, "McBryde-Thomas, flat-pole quartic*", "mbtfpq");
                ProjectionPseudoCylindrical mbtfps = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_mbtfps, Projections::G_mbtfps, Projections::FI_mbtfps, Projections::GI_mbtfps, "McBryde-Thomas, flat-pole sine I.*", "mbtfps");
                
                ProjectionPseudoCylindrical mbtfps2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_mbtfps2, Projections::G_mbtfps2, Projections::F_mbtfps2, Projections::G_mbtfps2, "McBryde-Thomas, flat-pole sine II.", "mbtfps2");
                ProjectionCylindrical merc = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_merc, Projections::G_merc, Projections::FI_merc, Projections::GI_merc, "Mercator*", "merc");
                ProjectionCylindrical mill = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_mill, Projections::G_mill, Projections::FI_mill, Projections::GI_mill, "Miller*", "mill"); 
                ProjectionPseudoCylindrical moll = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_moll, Projections::G_moll, Projections::FI_moll, Projections::GI_moll, "Mollweide*", "moll");
                ProjectionPseudoCylindrical nell = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_nell, Projections::G_nell, Projections::FI_nell, Projections::GI_nell, "Nell*", "nell");
                ProjectionPseudoCylindrical nell_h = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_nell_h, Projections::G_nell_h, Projections::FI_nell_h, Projections::GI_nell_h, "Nell-Hammer*", "nell_h");    
                ProjectionMiscellaneous nicol = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_nicol, Projections::G_nicol, Projections::FI_nicol, Projections::GI_nicol, "Nicolosi, globular*", "nicol");
                ProjectionPseudoCylindrical ortel = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_ortel, Projections::G_ortel, Projections::F_ortel, Projections::G_ortel, "Ortelius", "ortel");
                ProjectionAzimuthal ortho = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_ortho, Projections::G_ortho, Projections::FI_ortho, Projections::GI_ortho, "Orthographic*", "ortho");
                ProjectionPseudoCylindrical parab = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_parab, Projections::G_parab, Projections::F_parab, Projections::G_parab, "Parabolic", "parab");
                ProjectionMiscellaneous peiq = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_peiq, Projections::G_peiq, Projections::F_peiq, Projections::G_peiq, "Peirce, quincuncial", "peiq");
                ProjectionAzimuthal pers = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_pers, Projections::G_pers, Projections::FI_pers, Projections::GI_pers, "Perspective*", "pers");
                
                ProjectionAzimuthal persf = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_persf, Projections::G_persf, Projections::FI_persf, Projections::GI_persf, "Far-side, perspective*", "persf");
                ProjectionAzimuthal persn = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_persn, Projections::G_persn, Projections::FI_persn, Projections::GI_persn, "Near-side, perspective*", "persn");
                ProjectionPolyConic poly = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_poly, Projections::G_poly, Projections::FI_poly, Projections::GI_poly, "Hassler,  American, polyconic*", "poly");
                ProjectionPseudoCylindrical putp1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp1, Projections::G_putp1, Projections::FI_putp1, Projections::GI_putp1, "Putnins P1*", "putp1");
                ProjectionPseudoCylindrical putp2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp2, Projections::G_putp2, Projections::FI_putp2, Projections::GI_putp2, "Putnins P2*", "putp2");
                ProjectionPseudoCylindrical putp3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp3, Projections::G_putp3, Projections::FI_putp3, Projections::GI_putp3, "Putnins P3*", "putp3");
                ProjectionPseudoCylindrical putp3p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp3p, Projections::G_putp3p, Projections::FI_putp3p, Projections::GI_putp3p, "Putnins P3P*", "putp3p");
                ProjectionPseudoCylindrical putp4p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp4p, Projections::G_putp4p, Projections::FI_putp4p, Projections::GI_putp4p, "Putnins P4P*", "putp4p");
                ProjectionPseudoCylindrical putp5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp5, Projections::G_putp5, Projections::FI_putp5, Projections::GI_putp5, "Putnins P5*", "putp5");
                ProjectionPseudoCylindrical putp5p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp5p, Projections::G_putp5p, Projections::FI_putp5p, Projections::GI_putp5p, "Putnins P5P*", "putp5p");         
                
                ProjectionPseudoCylindrical putp6 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp6, Projections::G_putp6, Projections::FI_putp6, Projections::GI_putp6,"Putnins P6*", "putp6");
                ProjectionPseudoCylindrical putp6p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_putp6p, Projections::G_putp6p, Projections::F_putp6p, Projections::G_putp6p, "Putnins P6p*", "putp6p");         
                ProjectionPseudoCylindrical qua_aut = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_qua_aut, Projections::G_qua_aut, Projections::FI_qua_aut, Projections::GI_qua_aut, "Quartic, authalic*", "qua_aut");
                ProjectionPseudoCylindrical rpoly = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_rpoly, Projections::G_rpoly, Projections::FI_rpoly, Projections::GI_rpoly, "Rectangular, polyconic*", "rpoly");
                ProjectionPseudoCylindrical sinu = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_sinu, Projections::G_sinu, Projections::FI_sinu, Projections::GI_sinu, "Sinusoidal*", "sinu");     
                ProjectionAzimuthal solo = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_solo, Projections::G_solo, Projections::FI_solo, Projections::GI_solo, "Solovyev,azimuthal*", "solo");
                ProjectionAzimuthal stere = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_stere, Projections::G_stere, Projections::FI_stere, Projections::GI_stere, "Stereographic*", "stere");
                ProjectionAzimuthal twi = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_twi, Projections::G_twi, Projections::FI_twi, Projections::GI_twi, "Twilight, general vertical perspective*", "twi");
                ProjectionPseudoCylindrical urm5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_urm5, Projections::G_urm5, Projections::FI_urm5, Projections::GI_urm5, "Urmaev V.*", "urm5");
                ProjectionPolyConic vandg = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_vandg, Projections::G_vandg, Projections::FI_vandg, Projections::GI_vandg, "Van der Grinten I.*", "vandg");
                
                ProjectionPolyConic vandg2 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_vandg2, Projections::G_vandg2, Projections::FI_vandg2, Projections::GI_vandg2, "Van der Grinten II.*", "vandg2");
                ProjectionPolyConic vandg3 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_vandg3, Projections::G_vandg3, Projections::FI_vandg3, Projections::GI_vandg3, "Van der Grinten III.*", "vandg3");
                ProjectionPolyConic vandg4 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_vandg4, Projections::G_vandg4, Projections::FI_vandg4, Projections::GI_vandg4, "Van der Grinten IV.*", "vandg4");
                ProjectionPseudoCylindrical wag1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wag1, Projections::G_wag1, Projections::F_wag1, Projections::G_wag1, "Wagner I.*", "wag1");
                ProjectionPseudoCylindrical wag2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wag2, Projections::G_wag2, Projections::FI_wag2, Projections::GI_wag2, "Wagner II.*", "wag2");                
                ProjectionPseudoCylindrical wag3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wag3, Projections::G_wag3, Projections::FI_wag3, Projections::GI_wag3, "Wagner III.*", "wag3");
                ProjectionPseudoCylindrical wag4 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wag4, Projections::G_wag4, Projections::FI_wag4, Projections::GI_wag4, "Wagner IV.*", "wag4");
                ProjectionPseudoCylindrical wag6 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wag6, Projections::G_wag6, Projections::FI_wag6, Projections::GI_wag6, "Wagner VI.*", "wag6");
                ProjectionPseudoCylindrical wag7 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wag7, Projections::G_wag7, Projections::FI_wag7, Projections::GI_wag7, "Wagner VII.*", "wag7");
                ProjectionPseudoAzimuthal wer = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wer, Projections::G_wer, Projections::FI_wer, Projections::GI_wer, "Werner-Staab", "wer");
                
                ProjectionPseudoCylindrical weren = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_weren, Projections::G_weren, Projections::FI_weren, Projections::GI_weren, "Werenskiold I.*", "weren");
                ProjectionPseudoAzimuthal wiech = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wiech, Projections::G_wiech, Projections::F_wiech, Projections::G_wiech, "Wiechel", "wiech");
                ProjectionPseudoCylindrical wink1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wink1, Projections::G_wink1, Projections::FI_wink1, Projections::GI_wink1, "Winkel I.*", "wink1");
                ProjectionPseudoCylindrical wink2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wink2, Projections::G_wink2, Projections::FI_wink2, Projections::GI_wink2, "Winkel II.*", "wink2");
                ProjectionPseudoAzimuthal wintri = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::F_wintri, Projections::G_wintri, Projections::FI_wintri, Projections::GI_wintri, "Winkel, tripel*", "wintri");

                //Add projections to the list
                projections.add(adamh);
                projections.add(adams1);
                projections.add(adams2);
                projections.add(aea);
                projections.add(aeqd);
                projections.add(aitoff);
                projections.add(api);
                projections.add(apiel);
                projections.add(armad);
                projections.add(august);
                
                projections.add(bacon);
                projections.add(behr);
                projections.add(boggs);
                projections.add(bonne);
                projections.add(breus);
                projections.add(bries);
                projections.add(cc);
                projections.add(cea);
                projections.add(clar);
                projections.add(collg);
                
                projections.add(crast);
                projections.add(cwe);
                projections.add(denoy);
                projections.add(eck1);
                projections.add(eck2);
                projections.add(eck3);
                projections.add(eck4);
                projections.add(eck5);
                projections.add(eck6);
                projections.add(eckgr);
                
                projections.add(eisen);
                projections.add(eqc);
                projections.add(eqdc);
                projections.add(eqdc2);
                projections.add(eqdc3);
                projections.add(fahey);          
                projections.add(fouc);
                projections.add(fouc_s);
                projections.add(fourn);
                projections.add(fourn2);
                
                projections.add(gall);
                projections.add(gins4);
                projections.add(gins5);
                projections.add(gins6);
                projections.add(gins9);
                projections.add(gins8);
                projections.add(gnom);
                projections.add(goode);
                projections.add(guyou);
                projections.add(hammer);
                projections.add(hataea);
                projections.add(hire);
                projections.add(jam);
                projections.add(kav5);
                
                projections.add(kav7);
                projections.add(laea);
                projections.add(lagrng);
                projections.add(larr);
                projections.add(lask);
                projections.add(lcc);
                projections.add(leac);
                projections.add(leac2);
                projections.add(litt);
                projections.add(loxim);
                
                projections.add(maurer);
                projections.add(mbt_s);
                projections.add(mbt_s3);
                projections.add(mbtfpp);
                projections.add(mbtfpq);
                projections.add(mbtfps);
                projections.add(mbtfps2);
                projections.add(merc);
                projections.add(mill);
                projections.add(moll);
                projections.add(nell);
                projections.add(nell_h);
                
                projections.add(nicol); 
                projections.add(ortel);
                projections.add(ortho);
                projections.add(parab);
                projections.add(peiq);
                projections.add(pers);
                projections.add(persf);
                projections.add(persn);
                projections.add(poly);
                projections.add(putp1);
                
                projections.add(putp2);
                projections.add(putp3);
                projections.add(putp3p);
                projections.add(putp4p);
                projections.add(putp5);
                projections.add(putp5p);
                projections.add(putp6);
                projections.add(putp6p);
                projections.add(qua_aut);
                projections.add(rpoly);
                
                projections.add(sinu);
                projections.add(solo);
                projections.add(stere);
                projections.add(twi);
                projections.add(urm5);
                projections.add(vandg);
                projections.add(vandg2);
                projections.add(vandg3);
                projections.add(vandg4);
                projections.add(wag1);
                
                projections.add(wag2);
                projections.add(wag3);
                projections.add(wag4);
                projections.add(wag6);
                projections.add(wag7);
                projections.add(wer);
                projections.add(weren);
                projections.add(wiech);
                projections.add(wink1);
                projections.add(wink2);
                
                projections.add(wintri);
        
        }
        
        public static double F_def(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                return lat / RO;
        }

        public static double G_def(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                return lon / RO;
        }
        
        
        //Adams Hemisphere in a square
        public static double F_adamh(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double a = cos(lat / RO) * sin(lonr / RO);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in F_adamh coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double B = 0.5 * PI - lat / RO;
                final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in F_adamh coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in F_adamh coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double X =  R * 0.5 * (Ye - Xe) / sqrt(2) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_adamh coordinate function, ", "F_adamh > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_adamh(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double a = cos(lat / RO) * sin(lonr / RO);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in G_adamh coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double B = 0.5 * PI - lat / RO;
                final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in G_adamh coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in G_adamh coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double Y =  R * 0.5 * (Ye + Xe) / sqrt(2) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_adamh coordinate function, ", "G_adamh > MAX_FLOAT: ", Y);

                return Y;
        }


        //Adams World in a Square I
        public static double F_adams1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = (cos(phi) * sin(0.5 * lonr / RO) - sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in F_adams1 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = (cos(phi) * sin(0.5 * lonr / RO) + sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in F_adams1 coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in F_adams1 coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double X = R * Xe + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_adams1 coordinate function, ", "F_adams1 > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_adams1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = (cos(phi) * sin(0.5 * lonr / RO) - sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in G_adams1 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = (cos(phi) * sin(0.5 * lonr / RO) + sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in G_adams1 coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                final double  v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in G_adams1 coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);

                //Compute elliptic integral of the first kind
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                double Y = R * Ye + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_adams1 coordinate function, ", "G_adams1 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Adams World in a Square II
        public static double F_adams2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = cos(phi) * sin(0.5 * lonr / RO) ;

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in F_adams2 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = sin(phi);
                final double B = acos(b);
                 final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in F_adams2 coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in F_adams2 coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double X =  R * 0.5 * (Ye - Xe) / sqrt(2) + dx;
                
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_adams2 coordinate function, ", "F_adams2 > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_adams2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = cos(phi) * sin(0.5 * lonr / RO);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in G_adams2 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = sin(phi);
                final double B = acos(b);
                final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in G_adams2 coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in G_adams2 coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double Y =  R * 0.5 * (Ye + Xe) / sqrt(2) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_adams2 coordinate function, ", "G_adams2 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Albers equal area
        public static double F_aea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        { 
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double n = 0.5 * (sin(lat1 / RO) + sin(lat2 / RO));

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_aea coordinate function, ", "1 / n, n = 0:", n);

                final double B = cos(lat1 / RO) * cos(lat1 / RO);
                final double C = B + 2 * n * sin(lat1 / RO);
                final double D = C - 2 * n * sin(lat / RO);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in F_aea coordinate function, ", "D < 0: ", D);

                final double rho = R * sqrt(D) / n;

                final double X = rho * sin(n * lonr / RO) + dx;

                //Throw exception
                if (abs (X) > MAX_FLOAT )  
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_aea coordinate function, ", "F_aea > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_aea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
	
                final double n = 0.5 * (sin(lat1 / RO) + sin(lat2 / RO));

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_aea coordinate function, ", "1 / n, n = 0:", n);

                final double B = cos(lat1 / RO) * cos(lat1 / RO);
                final double C = B + 2 * n * sin(lat1 / RO);
                final double D = C - 2 * n * sin(lat / RO);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in G_aea coordinate function, ", "D < 0: ", D);

                final double rho = R * sqrt(D) / n;
                final double E = C - 2 * n * sin(lat1 / RO);

                //Throw exception
                if (E < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(E) in G_aea coordinate function, ", "E < 0: ", E);

                final double rho0 = R * sqrt(E) / n;

                final double Y = rho0 - rho * cos(n * lonr / RO) + dy;
        
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_aea coordinate function, ", "G_AEA > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_aea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_aea coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_aea coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double n = 0.5 * (sin(lat1 / RO) + sin(lat2 / RO));

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_leac2 coordinate function, ", "1.0 / n, n = 0:", n);

                final double A = cos(lat1 / RO) * cos(lat1 / RO);
                final double C = A + 2 * n * sin(lat1 / RO);
                final double E = C - 2 * n * sin(lat1 / RO);

                //Throw exception
                if (E < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(E) in FI_aea coordinate function, ", "E < 0: ", E);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R * sqrt(E) / n;
                final double B = rho0 - Yr;
                final double rho = sqrt(Xr * Xr + B * B);

                //Finding solution for lat
                double D = A / (2.0 * n) + sin (lat1 / RO) - n * rho * rho / (2 * R * R);

                //Throw exception
                if (D > 1.0 + ARGUMENT_ROUND_ERROR || D < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(D) in FI_aea coordinate function, ", "abs(D) > 1: ", D);

                //Correct D
                if (D > 1.0) D = 1.0;
                else if (D < -1.0) D = -1.0;

                final double lat = asin(D) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_aea coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_aea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_aea coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_aea coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double n = 0.5 * (sin(lat1 / RO) + sin(lat2 / RO));

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_aea coordinate function, ", "1.0 / n, n = 0:", n);

                final double A = cos(lat1 / RO) * cos(lat1 / RO);
                final double C = A + 2 * n * sin(lat1 / RO);
                final double E = C - 2 * n * sin(lat1 / RO);

                //Throw exception
                if (E < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(E) in GI_aea coordinate function, ", "E < 0: ", E);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R * sqrt(E) / n;
                final double B = rho0 - Yr;
                final double eps = atan2(Xr, B) * RO;

                final double lonr = eps / n;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_aea coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Azimuthal equidistant
        public static double F_aeqd(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double rho = R * (90 - lat) / RO;
                final double X = rho * sin(lonr/RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_aeqd coordinate function, ", "F_aeqd > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_aeqd(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double rho = R * (90 - lat) / RO;
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_aeqd coordinate function, ", "G_aeqd > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
                public static double FI_aeqd(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_aeqd coordinate function, ", "X > MAX_FLOAT: ", X);
                
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_aeqd coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                final double lat = 90 - dist / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_aeqd coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_aeqd(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_aeqd coordinate function, ", "X > MAX_FLOAT: ", X);
                
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_aeqd coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_aeqd coordinate function, ", "abs(lonr)  > 180.", lonr);
 
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Aitoff
        public static double F_aitoff(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                double X = 0;
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO) * cos(lonr / 2.0 / RO);

                //Throw exception
                if (abs(A) > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(A) in F_aitoff coordinate function, ", "A > 1: ", A);

                final double theta = acos(A);

                if (abs(theta) < MIN_FLOAT)
                        X = dx;

                else
                {
                        final double C = sin(lat / RO) / sin(theta);
                        double D = 1 - C * C;

                        //Throw exception
                        if (D < -ARGUMENT_ROUND_ERROR){
                                System.out.println(lat + " " + lon);
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in F_aitoff coordinate function, ", "D < 0: ", D);
                        }
                        //Correct D
                        if (D < 0.0) D = 0.0;
                      
                        X = 2.0 * R * theta * signum(lonr) * sqrt(D) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_aitoff coordinate function, ", "F_aitoff > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_aitoff(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                double Y = 0;

                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO) * cos(lonr / 2.0 / RO);

                //Throw exception
                if (A > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(A) in G_aitoff coordinate function, ", "A > 1: ", A);

                final double theta = acos(A);

                if (abs(theta) < MIN_FLOAT)
                        Y = dy;

                else
                {
                        final double C = sin(lat / RO) / sin(theta);

                        Y = R * theta * C + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_aitoff coordinate function, ", "G_aitoff > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_aitoff(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_aitoff coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_aitoff coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double theta = signum(Yr) * sqrt(Xr * Xr + 4 * Yr * Yr) / (2.0 * R) * RO;

                double arg = Yr * sin(theta / RO) / (R * theta / RO);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_aitoff coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_aitoff coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_aitoff(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_aitoff coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_aitoff coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double theta = signum(Yr) * sqrt(Xr * Xr + 4 * Yr * Yr) / (2.0 * R) * RO;

                double arg = Yr * sin(theta / RO) / (R * theta / RO);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_aitoff coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;
                double arg2 = Xr * sin(theta / RO) / (2 * R * theta /RO * cos (lat / RO));

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in GI_aitoff coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lonr = 2.0 * asin(arg2) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_aitoff coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Apianus
        public static double F_api(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if (abs(lonr) < MAX_ANGULAR_DIFF)
                {
                        X = dx;
                }

                else
                {
                        //Analogous to Bacon globular, but different Y
                        
                        //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_api coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);

                        final double F = ((PI / 2) *(PI / 2) * RO / abs(lonr) + abs(lonr) / RO) / 2;
                        final double Y = R * lat / RO;
                        final double G = F * F - Y * Y / (R * R);

                        //Throw exception
                        if (G < 0)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(G) in F_bacon coordinate function, ", "G < 0: ", G);

                        X = R * signum(lonr) * (abs(lonr) / RO - F + sqrt(G)) + dx;
                }
                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_api coordinate function, ", "F_api > MAX_FLOAT: ", X);

                return X;

        }


        public static double G_api(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_api coordinate function, ", "G_api > MAX_FLOAT: ", Y);

                return Y;
        }


        //Apianus Elliptical
        public static double F_apiel(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 2.0 * lat / (RO * PI);

                //Throw exception
                if (A > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(A) in F_apiel coordinate function, ", "A > 1: ", A);

                final double X = R * lonr / RO * cos(asin(A)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_apiel coordinate function, ", "F_apiel > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_apiel(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_apiel coordinate function, ", "G_apiel > MAX_FLOAT: ", Y);

                return Y;
        }
        
        public static double FI_apiel(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_apiel coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = (Y - dy) / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_apiel                          coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_apiel(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_apiel coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_apiel coordinate function, ", "Y > MAX_FLOAT: ", Y);

                
                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_apiel coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double lat = FI_apiel(X, Y, R, lat1, lat2, lon0, dx, dy, c);
                
                final double A = 2.0 * lat / (RO * PI);
                
                //Throw exception
                if (A > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(A) in GI_apiel coordinate function, ", "A > 1: ", A);
                
                final double lonr = (X - dx) / (R * cos(asin(A))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_apiel coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Armadillo
        public static double F_armad(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double lat_int = -atan(cos(0.5 * lonr / RO) / tan(20.0 / RO)) * RO;
                final double lat_ = (lat < lat_int) ? lat_int : lat;

                final double X = R * (1.0 + cos(lat_ / RO)) * sin(lonr / 2.0 / RO) + dx;
               
                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_armad coordinate function, ", "F_armad > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_armad(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double lat_int = -atan(cos(0.5 * lonr / RO) / tan(20.0 / RO)) * RO;
                final double lat_ = (lat < lat_int) ? lat_int : lat;

                final double C0 = (1.0 + sin(20.0 / RO) - cos(20.0 / RO)) / 2.0;
                final double Y =  R * (C0 + sin(lat_ / RO) * cos(20.0 / RO) - (1.0 + cos(lat_ / RO)) * sin(20.0 / RO) * cos(lonr / 2.0 / RO)) + dy;
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_armad coordinate function, ", "G_armad > MAX_FLOAT: ", Y);

                return Y;
        }
        
        public static double FI_armad(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_armad coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_armad coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Use the substitution u = tan(lat/2)
                final double C0 = (1.0 + sin(20.0 / RO) - cos(20.0 / RO)) / 2.0;
                final double A1 = Xr * sin(20.0 / RO);
                final double A2 = R * C0 - Yr;
                final double A = A1 * A1 + A2 * A2;
                final double B = 4.0 * R * (R * C0 - Yr) * cos(20.0 / RO);
                final double C = 4.0 * R * R * cos(20 / RO) * cos(20.0 / RO);
                final double D = 4.0 * R * R * sin(20 / RO) * sin(20.0 / RO);

                //Solve quartic equation
                List <Double> roots = new ArrayList();
                Quartic.solveR(A / D, B / D, (2.0 * A + C) / D, B / D, (A - D) / D, roots);

                //Exception, no real solution
                if (roots.size() == 0)
                {
                        //Exception
                        //std::cout << "error";
                        //throw MathInvalidArgumentException <T>("MathInvalidArgumentException: can not evaluate FI_armad coordinate function, ", "no real root of the qurtic equation.", 0);
                }

                //Find suitable root: tbe back substitution to the coordinate function
                double lat = 0;
                for ( double root : roots)
                {
                        double latn = 2.0 * atan(root) * RO;

                        if (abs(latn) <= MAX_LAT)
                        {
                                //Compare X, Xn with the threshold
                                final double lon = 2.0 * atan(Xr* sin(20.0 / RO) / (R * (C0 + sin(latn / RO) * cos(20.0 / RO)) - Yr)) * RO;
                                //final double Xn = F_armad(latn, lon, R, lat1, lat2, lon0, dx, dy, c);
                                final double Xn = R * (1.0 + cos(latn / RO)) * sin(lon / 2.0 / RO) + dx;

                                //The back subsitution was successful
                                if (abs(Xn - X) < ARGUMENT_ROUND_ERROR)
                                {
                                        lat = latn;
                                        break;
                                }
                        }
                }
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_armad coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_armad(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_apiel coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_apiel coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double lat = FI_armad(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double C0 = (1.0 + sin(20.0 / RO) - cos(20.0 / RO)) / 2.0;
                final double lonr = 2.0 * atan(Xr * sin(20.0 / RO) / (R * (C0 + sin(lat / RO) * cos(20.0 / RO)) - Yr)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_apiel coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //August Epicycloidal
        public static double F_august(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double C1 = sqrt(1 - tan(0.5 * lat / RO) * tan(0.5 * lat / RO));
                final double C = 1 + C1 * cos(0.5 * lonr / RO);

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_august coordinate function, ", "1 / C, C = 0:", C);

                //Lagrange coordinates
                final double xl = sin(0.5 * lonr / RO) * C1 / C;
                final double yl = tan(0.5 * lat / RO) / C;

                final double X = 4.0 * R * xl * (3.0 + xl * xl - 3.0 * yl * yl) / 3.0 + dx;
                
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_august coordinate function, ", "F_armad > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_august(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)        
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double C1 = sqrt(1 - tan(0.5 * lat / RO) * tan(0.5 * lat / RO));
                final double C = 1 + C1 * cos(0.5 * lonr / RO);

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_august coordinate function, ", "1 / C, C = 0:", C);

                //Lagrange coordinates
                final double xl = sin(0.5 * lonr / RO) * C1 / C;
                final double yl = tan(0.5 * lat / RO) / C;

                final double Y = 4.0 * R * yl * (3.0 + 3.0 * xl * xl - yl * yl) / 3.0 + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_august coordinate function, ", "G_armad > MAX_FLOAT: ", Y);

                return Y;
        }
        
        public static double FI_august(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_august coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_august coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double rho = 1.0 + 9.0 * ( Xr * Xr +  Yr * Yr )/ (64.0 * R * R);
                final double discr1 = rho * rho - 0.5625 * Yr * Yr / (R * R);

                //Throw exception
                if (discr1 < 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate FI_august coordinate function, ", "sqrt(discr1) < 0.", discr1);

                final double discr2 = 0.5 * (rho - sqrt(discr1));

                //Throw exception
                if (discr2 < 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate FI_august coordinate function, ", "sqrt(discr2) < 0.", discr2);

                final double eta = asin(sqrt(discr2))/ 3.0;

                //Throw exception
                if (abs(eta) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_august coordinate function, ", "1.0 / eta, eta = 0:", eta);

                final double arg = 3.0 * Yr / (8.0 * R * sin(3.0 * eta));
                final double xi = log(arg + sqrt(arg * arg - 1.0)) / 3.0;

                //Lagrange coordinates
                final double xl = 2.0 * sinh(xi) * cos(eta);
                final double yl = 2.0 * cosh(xi) * sin(eta);

                final double lat = 2.0 * atan(2.0 * yl / (1.0 + xl * xl + yl * yl)) * RO;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_august coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }
         
        public static double GI_august(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_august coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_august coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double rho = 1.0 + 9.0 * ( Xr * Xr +  Yr * Yr )/ (64.0 * R * R);
                final double discr1 = rho * rho - 0.5625 * Yr * Yr / (R * R);

                //Throw exception
                if (discr1 < 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_august coordinate function, ", "sqrt(discr1) < 0.", discr1);

                final double discr2 = 0.5 * (rho - sqrt(discr1));

                //Throw exception
                if (discr2 < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_august coordinate function, ", "sqrt(discr2) < 0.", discr2);

                final double eta = asin(sqrt(discr2)) / 3.0;

                //Throw exception
                if (abs(eta) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_august coordinate function, ", "1.0 / eta, eta = 0:", eta);

                final double arg = 3.0 * Yr / (8.0 * R * sin(3.0 * eta));
                final double xi = log(arg + sqrt(arg * arg - 1.0)) / 3.0;

                //Lagrange coordinates
                final double xl = 2.0 * sinh(xi) * cos(eta);
                final double yl = 2.0 * cosh(xi) * sin(eta);

                final double lonr = 2.0 * signum(Xr) * atan(2.0 * xl / (1.0 - xl * xl - yl * yl)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_apiel coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        //Bacon Globular
        public static double F_bacon(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if (abs(lonr) < MAX_ANGULAR_DIFF)
                {
                        X = dx;
                }

                else
                {
                        final double F = ((PI / 2) *(PI / 2) * RO / abs(lonr) + abs(lonr) / RO) / 2;
                        final double Y = R * PI / 2.0 * sin(lat / RO);
                        double G = F * F - Y * Y / (R * R);

                        //Throw exception
                        if (G < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(G) in F_bacon coordinate function, ", "G < 0: ", G);

                        //Correct G
                        if (G < 0.0) G = 0.0;

                        X = R * signum(lonr) * (abs(lonr) / RO - F + sqrt(G)) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_bacon coordinate function, ", "F_bacon > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_bacon(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * (PI / 2) * sin(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_bacon coordinate function, ", "G_bacon > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Behrmann Cylindrical
        public static double F_behr(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(30.0 / RO) / RO + dx;


                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_behr coordinate function, ", "F_behr > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_behr(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * sin(lat / RO) / cos(30.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_behr coordinate function, ", "G_behr > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_behr(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_behr coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;

                final double lat1_cos = cos(30.0 / RO);
                final double arg = Yr * lat1_cos / R;
                        
                //Throw exception
                if (abs(arg) > 1.0)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_behr coordinate function, ", "abs(asin(arg)) > 1.0.", arg);
             
                //Inverse equation
                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_behr coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_behr(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_behr coordinate function, ", "X > MAX_FLOAT: ", X);

                //Inverse equation
                final double Xr = X - dx;

                final double lat1_cos = cos(30.0 / RO);
                final double lonr = Xr / (R * lat1_cos) * RO;
	
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_behr coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Boggs Eumorphic
        public static double F_boggs(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 2.00276;
                final double A = 1.11072;

                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_moll);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_moll);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double B = 1.0 / cos(lat / RO) + A / cos(theta / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_boggs coordinate function, ", "1.0 / B, B = 0:", B);

                final double X = cx * R * lonr / RO / B + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_boggs coordinate function, ", "F_boggs > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_boggs(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 0.49931;
                final double D = sqrt(2.0);
        
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_moll);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_moll);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy * R * (lat / RO + D * sin(theta / RO)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_boggs coordinate function, ", "G_boggs > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_boggs(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_boggs coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_boggs coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 0.49931;
                final double D = sqrt(2.0);

                final double arg = Yr / (cy * R);
                
                final double theta0 = -FITheta_boggs(arg, 0.0) / FIThetaDer_boggs(arg, 0.0);
                
                //Throw exception
                if (abs(theta0) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_boggs coordinate function, ", "abs(theta0)  > 90.", theta0);

                FTheta ft = new FTheta(arg, Projections::FITheta_boggs);
                FThetaDer ftd = new FThetaDer(arg, Projections::FIThetaDer_boggs);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double lat = arg * RO  - D * sin(theta / RO) * RO;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_boggs coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_boggs(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_boggs coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_boggs coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double cx = 2.00276;
                final double cy = 0.49931;
                final double C = 1.11072;
                final double D = sqrt(2.0);
                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                final double arg = Yr / (cy * R);
                
                final double theta0 = -FITheta_boggs(arg, 0.0) / FIThetaDer_boggs(arg, 0.0);
                
                //Throw exception
                if (abs(theta0) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_boggs coordinate function, ", "abs(theta0)  > 90.", theta0);

                FTheta ft = new FTheta(arg, Projections::FITheta_boggs);
                FThetaDer ftd = new FThetaDer(arg, Projections::FIThetaDer_boggs);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double lat = arg * RO  - D * sin(theta / RO) * RO;
    
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_boggs coordinate function, ", "abs(lat)  > 90.", lat);

                final double E = 1.0 / cos(lat / RO) + C / cos(theta / RO);

                //Throw exception
                if (abs(E) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_boggs coordinate function, ", "1.0 / E, E = 0:", E);

                final double lonr = Xr * E / ( cx * R) * RO;
                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                //Throw exception
                if (abs(lon) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_boggs coordinate function, ", "abs(lon)  > 180.", lon);
                
                return lon;
        }
        

        public static double FITheta_boggs(final double arg, final double theta)
        {
                // f(theta, Y, R, cy) = asin[1/pi * (2 * theta + sin(2 * theta))] + sqrt(2.0) * sin(theta) - Y / (2 * R)
                final double c1 = 1.0 / PI;
                final double c2 = sqrt(2.0);

                return asin(c1 * (2.0 * theta / RO + sin(2.0 * theta / RO))) + c2 * sin(theta / RO) - arg;
        }


        public static double FIThetaDer_boggs(final double lat, final double theta)
        {
                // df / dtheta = 2 / pi * (1 + cos(2 * theta)) / sqrt[1 - 1/pi * (2 * theta + sin(2 * theta))] + sqrt(2.0) * cos(theta)
                final double c1 = 1.0 / PI;
                final double c2 = sqrt(2.0);

                final double A = c1 *  (2.0 * theta / RO + sin(2.0 * theta / RO));
                double B = 1 - A * A;

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate FI_boggs coordinate function, ", "1 / B, B = 0:", B);

                return (2.0 * c1 * (1.0 + cos(2.0 * theta / RO)) / sqrt(B) + c2 * cos(theta / RO)) / RO;
        }
        
        
        //Bonne
        public static double F_bonne(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(lat1) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat1) in F_bonne coordinate function, ", "lat1 = +-90: ", lat1);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_bonne coordinate function, ", "1 / A, A = 0:", A);

                final double rho0 = R / A;
                final double rho = rho0 + R * (lat1 - lat) / RO;
                final double X = rho * sin(R * lonr / RO * cos(lat / RO) / rho) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_bonne coordinate function, ", "F_bonne > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_bonne(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(lat1) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat1) in G_bonne coordinate function, ", "lat1 = +-90: ", lat1);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_bonne coordinate function, ", "1 / A, A = 0:", A);

                final double rho0 = R / A;
                final double rho = rho0 + R * (lat1 - lat) / RO;

                //Throw exception
                if (abs(rho) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_bonne coordinate function, ", "1 / B, B = 0:", rho);

                final double Y = rho0 - rho * cos(R * lonr / RO * cos(lat / RO) / rho) + dy;

                return Y;
        }

        
        public static double FI_bonne(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_bonne coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_bonne coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_bonne coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_bonne coordinate function, ", "1.0 / A, A = 0:", A);

                final double rho0 = R / A;
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double B = rho0 - Yr;
                final double rho = signum(lat1) * sqrt(Xr * Xr + B * B);
                final double lat = (rho0 - rho) / R * RO + lat1;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_bonne coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_bonne(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_bonne coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_bonne coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_bonne coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_bonne coordinate function, ", "1.0 / A, A = 0:", A);

                final double rho0 = R / A;
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double B = rho0 - Yr;
                final double rho = signum(lat1) * sqrt(Xr * Xr + B * B);
                final double lat = (rho0 - rho) / R * RO + lat1;
                final double lonr = rho / R / cos(lat / RO) * atan2(Xr, B) * RO;

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                //Throw exception
                if (abs(lon) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_bonne coordinate function, ", "abs(lon)  > 180.", lon);
                
                return lon;
        }
        

        //Breusignm
        public static double F_breus(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2.0 / RO;
                final double B = cos(A);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in F_breus coordinate function, ", "B < 0: ", B);

                final double rho = 2.0 * R * sin(A) * sqrt(B);
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_breus coordinate function, ", "F_breus > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_breus(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2.0 / RO;
                final double B = cos(A);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in G_breus coordinate function, ", "B < 0: ", B);

                final double rho = 2.0 * R * sin(A) * sqrt(B);
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_breus coordinate function, ", "G_breus > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Briesenmeister
        public static double F_bries(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double W = 0.5;
                final double M = sqrt(1.75 / 2.0);
                final double D = 2.0 / (1.0 + cos(lat / RO) * cos(W * lonr / RO));
                final double X = R * sqrt(D) / W * M * cos(lat / RO) * sin(W * lonr / RO) + dx;
                
                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_bries coordinate function, ", "F_bries > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_bries(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double W = 0.5;
                final double M = sqrt(1.75 / 2.0);
                final double D = 2.0 / (1.0 + cos(lat / RO) * cos(W * lonr / RO));
                final double Y = R * sqrt(D) / M * sin(lat / RO) + dy;
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_bries coordinate function, ", "G_bries > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_bries(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_bries coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_bries coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double F = 0.25;
                final double M = sqrt(1.75 / 2.0);
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double A = 4.0 * R * R * Yr * Yr * M * M - Yr * Yr * Yr * Yr * M * M * M * M - F * F * Xr * Xr * Yr * Yr;
                
                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in FI_bries coordinate function, ", "A < 0: ", A);

                double B = sqrt(A) / (2.0 * R * R);
                
                //Throw exception
                if (abs(B) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(B) in FI_bries coordinate function, ", "abs(B) > 1: ", B);

                final double lat = signum(Yr) * asin(B) * RO;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_bries coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_bries(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_bries coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_bries coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double lat = FI_bries(X, Y, R, lat1, lat2, lon0, dx, dy, c);
                
                final double A = sin(lat / RO);
                final double B = cos(lat / RO);
                final double F = 0.25;
                final double M = sqrt(1.75 / 2.0);
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double C = 2 * R * R * A * A - Yr * Yr * M * M;
                double D = Yr * Yr * Yr * Yr * M * M * M * M * B * B - C * C;
                
                //Throw exception
                if (D < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in GI_bries coordinate function, ", "D < 0: ", D);

                //Throw exception
                if (D < 0.0)
                        D = 0.0;

                final double lonr = 1.0 / F * signum(X) * atan2(sqrt(D), C) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_bries coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Central Cylindrical
        public static double F_cc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr / RO * cos (lat1 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_cc coordinate function, ", "F_cc > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_cc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(abs(lat) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in G_cc coordinate function, ", "lat = +-90: ", lat);

                final double Y = R * tan(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_cc coordinate function, ", "G_cc > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_cc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_cc coordinate function, ", "Y > MAX_FLOAT: ", Y);
       
                //Inverse equation
                final double lat = atan((Y - dy) / R) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_cc coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_cc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_cc coordinate function, ", "X > MAX_FLOAT: ", X);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_cc coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double lonr = (X - dx) / (R * cos(lat1 / RO)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_cc coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //Cylindrical Equal Area
        public static double F_cea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_cea coordinate function, ", "F_cea > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_cea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * sin(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_cea coordinate function, ", "G_cea > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_cea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_cea coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;

                final double arg = Yr / R;
                        
                //Throw exception
                if (abs(arg) > 1.0)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_cea coordinate function, ", "abs(asin(arg)) > 1.0.", arg);
             
                //Inverse equation
                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_cea coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_cea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_cea coordinate function, ", "FI_cea > MAX_FLOAT: ", X);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_cea coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double Xr = X - dx;

                final double lonr = Xr / (R *  cos(lat1 / RO)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_cea coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Clark Perspective
        public static double F_clar(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.368;

                return F_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }


        public static double G_clar(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.368;

                return G_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }
        
        
        public static double FI_clar(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.368;

                return FI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }
        
        
        public static double GI_clar(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.368;

                return GI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }

        
        //Collignon
        public static double F_collg(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 2.0 / sqrt(PI);
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = cx * R * lonr / RO * sqrt(1.0 - sin(lat / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_collg coordinate function, ", "F_collg > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_collg(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = sqrt(PI);
                
                final double Y = cy * R * (1.0 - sqrt(1.0 - sin(lat / RO))) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_collg coordinate function, ", "G_collg > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_collg(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_collg coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_collg coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = sqrt(PI);
                final double A = 1.0 - Yr / (R * cy);

                double arg = 1.0 - A * A + dy;

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_collg coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_collg coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_collg(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_collg coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_collg coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_collg(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 2.0 / sqrt(PI);
                final double Xr = X - dx;

                final double lonr = Xr / (R * cx * sqrt(1.0 - sin(lat / RO))) * RO;    
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_collg coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Craster Parabolic
        public static double F_crast(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = sqrt(3.0 / PI);
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * cx * lonr / RO * (2.0 * cos(2.0 * lat / 3.0 / RO) - 1) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_crast coordinate function, ", "F_crast > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_crast(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = sqrt(3.0 * PI);
                
                final double Y = R * cy * sin(lat / 3.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_crast coordinate function, ", "G_crast > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_crast(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_crast coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = sqrt(3.0 * PI);

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_crast coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = 3.0 * asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_crast coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_crast(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_crast coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_crast coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_crast(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double cx = sqrt(3.0 / PI);;
                final double lonr = Xr / (R * cx * (2.0 * cos(2.0 / 3.0 * lat / RO) - 1.0)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_crast coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Conformal world in ellipse
        public static double F_cwe(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta = 23.8958;
                final double ks = sin(theta / RO);
                final double kc = cos(theta / RO);

                //Compute elliptic integrals of the first kind
                final double K = NumIntegration.getInEllipticIntegral1(kc, 0.5 * PI, 1.0e-14);

                final double u1 = 2 * (1 - kc) * cos(lat / RO);
                final double v1 = (1 + kc) * (1 + cos(lat / RO) * cos(lonr / RO));
                final double A = ks * ks * (1 - cos(lat / RO) * cos(lonr / RO));
                final double B = u1 - v1;
                final double C = u1 + v1;
                double D = A * A - 4 * kc * B * C;

                //Throw exception
                if (D < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in F_cwe coordinate function, ", "D < 0: ", D);

                //Correct D
                if (D < 0.0) D = 0.0;

                final double R1 = A - sqrt(D);

                // Throw exception
                if (abs(kc) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_cwe coordinate function, ", "1.0 / kc, kc = 0: ", kc);

                // Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_cwe coordinate function, ", "1.0 / C, C = 0: ", C);

                double R2 = R1 / (2 * C * kc);

                //Throw exception
                if (R2 > 1.0 + ARGUMENT_ROUND_ERROR || R2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(R2) in F_cwe coordinate function, ", "abs(R2) > 1: ", R2);

                //Correct R2
                if (R2 > 1.0) R2 = 1.0;
                else if (R2 < -1.0) R2 = -1.0;

                final double lambda = -asin(R2);

                // Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_cwe coordinate function, ", "1.0 / B, B = 0: ", B);

                double E = 1 - R1 * R1 / (4 * B * B);

                //Throw exception
                if (E < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(E) in F_cwe coordinate function, ", "E < 0: ", E);

                //Correct E
                if (E < 0.0) E = 0.0;

                // Throw exception
                if (abs(ks) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_cwe coordinate function, ", "1.0 / ks, ks = 0: ", ks);

                double F = sqrt(E) / ks;

                //Throw exception
                if (F > 1.0 + ARGUMENT_ROUND_ERROR || F < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(F) in F_cwe coordinate function, ", "abs(F) > 1: ", F);

                //Correct F
                if (F > 1.0) F = 1.0;
                else if (F < -1.0) F = -1.0;

                final double phi = asin(F);

                //Compute elliptic integrals of the first kind
                final double u2 = NumIntegration.getInEllipticIntegral1(ks, phi, 1.0e-14);
                final double v2 = K - NumIntegration.getInEllipticIntegral1(kc, lambda, 1.0e-14);

                final double u3 = exp(PI * u2 / (4 * K));
                final double v3 = PI * v2 / (4 * K);

                final double X = R * signum(lonr) * 0.5 * (u3 + 1.0 / u3) * sin(v3) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_cwe coordinate function, ", "F_cwe > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_cwe(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta = 23.8958;
                final double ks = sin(theta / RO);
                final double kc = cos(theta / RO);

                //Compute elliptic integrals of the first kind
                final double K = NumIntegration.getInEllipticIntegral1(kc, 0.5 * PI, 1.0e-14);

                final double u1 = 2 * (1 - kc) * cos(lat / RO);
                final double v1 = (1 + kc) * (1 + cos(lat / RO) * cos(lonr / RO));
                final double A = ks * ks * (1 - cos(lat / RO) * cos(lonr / RO));
                final double B = u1 - v1;
                final double C = u1 + v1;
                double D = A * A - 4 * kc * B * C;

                //Throw exception
                if (D < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in G_cwe coordinate function, ", "D < 0: ", D);

                //Correct D
                if (D < 0.0) D = 0.0;

                final double R1 = A - sqrt(D);

                // Throw exception
                if (abs(kc) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_cwe coordinate function, ", "1.0 / kc, kc = 0: ", kc);

                // Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_cwe coordinate function, ", "1.0 / C, C = 0: ", C);

                double R2 = R1 / (2 * C * kc);

                //Throw exception
                if (R2 > 1.0 + ARGUMENT_ROUND_ERROR || R2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(R2) in G_cwe coordinate function, ", "abs(R2) > 1: ", R2);

                //Correct R2
                if (R2 > 1.0) R2 = 1.0;
                else if (R2 < -1.0) R2 = -1.0;

                final double lambda = -asin(R2);

                // Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_cwe coordinate function, ", "1.0 / B, B = 0: ", B);

                double E = 1 - R1 * R1 / (4 * B * B);

                //Throw exception
                if (E < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(E) in G_cwe coordinate function, ", "E < 0: ", E);

                //Correct E
                if (E < 0.0) E = 0.0;

                // Throw exception
                if (abs(ks) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_cwe coordinate function, ", "1.0 / ks, ks = 0: ", ks);

                double F = sqrt(E) / ks;

                //Throw exception
                if (F > 1.0 + ARGUMENT_ROUND_ERROR || F < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(F) in G_cwe coordinate function, ", "abs(F) > 1: ", F);

                //Correct F
                if (F > 1.0) F = 1.0;
                else if (F < -1.0) F = -1.0;

                final double phi = asin(F);

                //Compute elliptic integrals of the first kind
                final double u2 = NumIntegration.getInEllipticIntegral1(ks, phi, 1.0e-14);
                final double v2 = K - NumIntegration.getInEllipticIntegral1(kc, lambda, 1.0e-14);

                final double u3 = exp(PI * u2 / (4 * K));
                final double v3 = PI * v2 / (4 * K);

                final double Y = R * signum(lat) * 0.5 * (u3 - 1.0 / u3) * cos(v3) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_cwe coordinate function, ", "G_cwe > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Denoyer Semi-Elliptical
        public static double F_denoy(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = abs(lonr) / RO;
                final double B = 0.95 - 1.0 / 12 * A + 1.0 / 600 * A * A * A;
                final double C = 0.9 * lat / RO + 0.03 * pow(lat / RO, 5);

                final double X =  R * lonr / RO * cos(B * C) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_denoy coordinate function, ", "F_denoy > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_denoy(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_denoy coordinate function, ", "G_denoy > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_denoy(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_denoy coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_denoy coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;

                final double lat = Yr / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_denoy coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_denoy(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_denoy coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_denoy coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Assign non-zero root
                final double lonr0 = 0;

                //Inverse equation
                FILon fl = new FILon(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILon_denoy);
                FILonDer fld = new FILonDer(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILonDer_denoy);
                double lonr = NewtonRaphson.findRoot(fl::function, fld::function, lonr0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
        
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_denoy coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        public static double FILon_denoy(final double lon_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                // f(lon, Y, R) = cos[(0.95 - |lon| / 12 + |lon^3| / 600)(0.9 * Y / R + 0.03 * Y^5 / R^5)] - X / (R * lon)
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double A = abs(lon_i) / RO;
                final double B = 0.95 - 1.0 / 12 * A + 1.0 / 600 * A * A * A;
                final double C1 = 0.9 * Yr / R + 0.03 * pow(Yr / R, 5);
                final double C2 = Xr / R;

                return R * lon_i / RO * cos(B * C1) - Xr;
        }


        public static double FILonDer_denoy(final double lon_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                // df / lon = [sign(lon)/12 - 3 * lon^2 / 600 * sign(lon)] * sin[(0.95 - |lon| / 12 + |lon^3| / 600)(0.9 * Y / R + 0.03 * Y^5 / R^5)] + X / (R * lon^2)
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double A = abs(lon_i) / RO;
                final double B = 0.95 - 1.0 / 12 * A + 1.0 / 600 * A * A * A;
                final double C1 = 0.9 * Yr / R + 0.03 * pow(Yr / R, 5);
                final double D = signum(lon_i) / 12 - 3 * lon_i * lon_i / (600 * RO * RO) * signum(lon_i);

                return (R * cos(B * C1) + R * C1 * D * lon_i / RO * sin(B * C1)) / RO;     
        }


        //Eckert I
        public static double F_eck1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 2.0 * sqrt(2.0 / (3.0 * PI));
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = cx * R * lonr / RO * (1 - abs(lat / (PI * RO))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eck1 coordinate function, ", "F_eck1 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eck1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 2.0 * sqrt(2.0 / (3.0 * PI));
                
                final double Y = cy * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eck1 coordinate function, ", "G_eck1 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_eck1(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck1 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck1 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 2.0 * sqrt(2.0 / (3.0 * PI));

                final double lat = Yr / (cy * R) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eck1 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_eck1(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck1 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck1 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_eck1(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 2.0 * sqrt(2.0 / (3.0 * PI));
                final double Xr = X - dx;

                final double lonr = Xr / (R * cx * (1.0 - abs(lat / PI / RO))) * RO;   
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_eck1 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Eckert II
        public static double F_eck2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 2.0 / sqrt(6.0 * PI);
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = cx * R * lonr / RO * sqrt(4.0 - 3.0 * sin(abs(lat / RO))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eck2 coordinate function, ", "F_eck2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eck2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = sqrt(2.0 * PI / 3.0);
                
                final double Y = cy * R* (2.0 - sqrt(4.0 - 3.0 * sin(abs(lat / RO)))) * signum(lat) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eck2 coordinate function, ", "G_eck2 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_eck2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = sqrt(2.0 * PI / 3.0);
                final double A = 2.0 - abs(Yr) / (R * cy);

                double arg = (4.0 - A * A) / 3.0;

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_eck2 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = signum(Y) * asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eck2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_eck2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_eck2(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 2.0 / sqrt(6.0 * PI);
                final double Xr = X - dx;

                final double lonr = Xr / (R * cx * sqrt(4.0 - 3.0 * sin(abs(lat) / RO))) * RO;   

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_eck2 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Eckert III
        public static double F_eck3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 2.0 / sqrt(PI * (4.0 + PI));
                final double A = 1.0;
                final double B = 4.0;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in F_eck3 coordinate function, ", "D < 0: ", D);

                final double X = cx * R * lonr / RO * (A + sqrt(D)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eck3 coordinate function, ", "F_eck3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eck3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 4.0 / sqrt(PI * (4.0 + PI));

                final double Y = cy * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eck3 coordinate function, ", "G_eck3 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_eck3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck3 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 4.0 / sqrt(PI * (4.0 + PI));

                final double lat = Yr / (R * cy) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eck3 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_eck3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck3 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_eck3(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 2.0 / sqrt(PI * (4.0 + PI));
                final double A = 1.0;
                final double B = 4.0;
                final double Xr = X - dx;
                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in GI_eck3 coordinate function, ", "D < 0: ", D);

                final double lonr = Xr / (R * cx * (A + sqrt(D))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_eck3 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Eckert IV
        public static double F_eck4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double c1 = 2.0 / sqrt(PI * (4.0 + PI));
                
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double latr = lat / RO;
                
                final double theta0 = (0.895168 * latr + 0.0218849 * latr * latr * latr + 0.00806809 * latr * latr * latr * latr * latr) * RO;
                FTheta ft = new FTheta(lat, Projections::FTheta_eck4);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_eck4);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = c1 * R * lonr / RO * (1.0 + cos(theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eck4 coordinate function, ", "F_eck4 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eck4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double c2 = 2.0 * sqrt(PI / (4.0 + PI));
                
                final double latr = lat / RO;
                
                final double theta0 = (0.895168 * latr + 0.0218849 * latr * latr * latr + 0.00806809 * latr * latr * latr * latr * latr) * RO;
                FTheta ft = new FTheta(lat, Projections::FTheta_eck4);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_eck4);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = c2 * R * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eck4 coordinate function, ", "G_eck4 > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_eck4(final double lat, final double theta)
        {
                final double cp = 2.0 + PI / 2.0;
                return theta / RO + 0.5 * sin(2.0 * theta / RO) + 2.0 * sin(theta / RO) - cp * sin(lat / RO);
        }


        public static double FThetaDer_eck4(final double lat, final double theta)
        {
                return (1.0 + cos(2.0 * theta / RO) + 2.0 * cos(theta / RO))/RO;
        }
        
        
        public static double FI_eck4(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck4 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck4 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                final double c2 = 2.0 * sqrt(PI / (4.0 + PI));

                double arg = Yr / (R * c2);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg  < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_eck4 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;
                
                final double theta = asin(arg) * RO;

                final double cp = 2.0 + PI / 2.0;
                double arg2 = (theta / RO + 0.5 * sin(2 * theta / RO) + 2 * sin(theta / RO)) / cp;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_eck4 coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eck4 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_eck4(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck4 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck4 coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_eck4(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double c2 = 2.0 * sqrt(PI / (4.0 + PI));
                final double Xr = X - dx;
                final double Yr = Y - dy;
                double arg = Yr / (R * c2);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_eck4 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double c1 = 2.0 / sqrt(PI * (4 + PI));
                final double lonr = Xr / (c1 * R * (1.0 + cos(theta / RO))) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eck4 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Eckert V
        public static double F_eck5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr / RO * (1 + cos(lat / RO)) / sqrt(2.0 + PI) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eck5 coordinate function, ", "F_eck5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eck5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * R * lat / RO / sqrt(2.0 + PI) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eck5 coordinate function, ", "G_eck5 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_eck5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck5 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                final double Yr = Y - dy;
                double arg = 0.5 * Yr * sqrt(2 + PI) / R;

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg  < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_eck5 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eck6 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_eck5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck5 coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_eck5(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double lonr = Xr * sqrt(2 + PI) / (R * (1 + cos(lat / RO))) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eck6 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Eckert VI
        public static double F_eck6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
            
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_eck6);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_eck6);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
        
                final double X = R * (1 + cos(theta / RO)) * lonr / RO / sqrt(2.0 + PI) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eck6 coordinate function, ", "F_eck6 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eck6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_eck6);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_eck6);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 2.0 * R * theta / sqrt(2.0 + PI) / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eck6 coordinate function, ", "G_eck6 > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_eck6(final double lat, final double theta)
        {
                return theta / RO + sin(theta / RO) - (1 + PI / 2) * sin(lat / RO);
        }


        public static double FThetaDer_eck6(final double lat, final double theta)
        {
                return (1 + cos(theta / RO)) / RO;
        }

        
        public static double FI_eck6(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck6 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eck6 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                final double theta = Yr * sqrt(PI + 2.0) / (2.0 * R) * RO;
                double A = 2 * (sin(theta / RO) + theta / RO) / (PI + 2.0);

                //Throw exception
                if (A > 1.0 + ARGUMENT_ROUND_ERROR || A < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(A) in FI_eck6 coordinate function, ", "abs(A) > 1: ", A);

                //Correct A
                if (A > 1.0) A = 1.0;
                else if (A < -1.0) A = -1.0;

                final double lat = asin(A) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eck6 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_eck6(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck6 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck6 coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_eck6(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double theta = Yr * sqrt(PI + 2.0) / (2.0 * R) * RO;
                final double A = cos(0.5 * theta / RO);
                final double lonr = Xr * sqrt(PI + 2.0) / (2 * R * A * A) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eck6 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Eckert-Greifendorff
        public static double F_eckgr(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double W = 0.25;
                final double D = 2.0 / (1.0 + cos(lat / RO) * cos(W * lonr / RO));
                final double X = R * sqrt(D) / W * cos(lat / RO) * sin(W * lonr / RO) + dx;
                
                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eckgr coordinate function, ", "F_eckgr > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eckgr(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double W = 0.25;
                final double D = 2.0 / (1.0 + cos(lat / RO) * cos(W * lonr / RO));
                final double Y = R * sqrt(D) * sin(lat / RO) + dy;
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eckgr coordinate function, ", "G_eckgr > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_eckgr(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eckgr coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eckgr coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double F = 0.25;
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double A = 4.0 * R * R - Yr * Yr - F * F * Xr * Xr;
                
                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in FI_eckgr coordinate function, ", "A < 0: ", A);

                final double B = Yr * sqrt(A) / (4.0 * R * R);
                
                //Throw exception
                if (abs(B) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(B) in FI_eckgr coordinate function, ", "abs(B) > 1: ", B);

                final double lat = asin(B) * RO;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eckgr coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_eckgr(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eckgr coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eckgr coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double lat = FI_eckgr(X, Y, R, lat1, lat2, lon0, dx, dy, c);
                
                final double A = sin(lat / RO);
                final double B = cos(lat / RO);
                final double F = 0.25;
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double C = 2 * R * R * A * A - Yr * Yr;
                double D = Yr * Yr * Yr * Yr * B * B - C * C;
                
                //Throw exception
                if (D < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in GI_eckgr coordinate function, ", "D < 0: ", D);

                //Throw exception
                if (D < 0.0)
                        D = 0.0;

                final double lonr = 1.0 / F * signum (X) * atan2(sqrt(D), C) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_eckgr coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Eisenlohr
        public static double F_eisen(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double S1 = sin(0.5 * lonr / RO);
                final double C1 = cos(0.5 * lonr / RO);
                final double D = cos(0.5 * lat / RO) + C1 * sqrt(2.0 * cos(lat / RO));

                //Throw exception
                if (abs(D) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_eisen coordinate function, ", "1 / D, D = 0:", D);

                final double TT = sin(0.5 * lat / RO) / D;
                final double C = sqrt(2.0 / (1 + TT * TT));
                final double E = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 + S1);
                final double F = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 - S1);

                //Throw exception
                if (abs(F) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_eisen coordinate function, ", "1 / E, E = 0:", E);

                final double G = E / F;

                if (G < 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(G) in F_eisen coordinate function, ", "G < 0: ", G);

                final double V = sqrt(G);

                //Throw exception
                if (abs(V) < MIN_FLOAT)
                        throw new  MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_eisen coordinate function, ", "1 / V, V = 0:", V);

                final double X = R * (3.0 + sqrt(8))*(-2.0 * log(V) + C * (V - 1.0 / V)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_eisen coordinate function, ", "F_eisen > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eisen(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double S1 = sin(0.5 * lonr / RO);
                final double C1 = cos(0.5 * lonr / RO);
                final double D = cos(0.5 * lat / RO) + C1 * sqrt(2.0 * cos(lat / RO));

                //Throw exception
                if (abs(D) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_eisen coordinate function, ", "1 / D, D = 0:", D);

                final double TT = sin(0.5 * lat / RO) / D;
                final double C = sqrt(2.0 / (1 + TT * TT));
                final double E = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 + S1);
                final double F = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 - S1);

                //Throw exception
                if (abs(F) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_eisen coordinate function, ", "1 / E, E = 0:", E);

                final double G = E / F;

                //Throw exception
                if (G < 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(G) in G_eisen coordinate function, ", "G < 0: ", G);

                final double V = sqrt(G);

                //Throw exception
                if (abs(V) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_eisen coordinate function, ", "1 / V, V = 0:", V);

                //Throw exception
                if ((abs(TT)% 90 < MIN_FLOAT) && (abs(TT) > MIN_FLOAT))
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate atan(TT) in G_eisen coordinate function, ", "TT = PI: ", TT);

                final double Y = R * (3.0 + sqrt(8)) * (-2.0 * atan(TT) + C * TT * (V + 1.0 / V)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eisen coordinate function, ", "G_eisen > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Equidistant Cylindrical 
        public static double F_eqc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eqc coordinate function, ", "F_eqc > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eqc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eqc coordinate function, ", "G_eqc > MAX_FLOAT: ", Y);

                return Y;
        }
 
        
        public static double FI_eqc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eqc coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = (Y - dy) / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eqc coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_eqc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eqc coordinate function, ", "X > MAX_FLOAT: ", X);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqc coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double lonr = (X - dx) / (R * cos(lat1 / RO)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_eqc coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Equidistant Conic (true parallel lat1)
        public static double F_eqdc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_eqdc coordinate function, ", "1 / A, A = 0:", A);

                final double rho0 = R / A;
                final double rho = rho0 + R * (lat1 - lat) / RO;
                final double n = sin(lat1 / RO);
                final double X = rho * sin(n * lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eqdc coordinate function, ", "F_eqdc > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eqdc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_eqdc coordinate function, ", "1 / A, A = 0:", A);

                final double rho0 = R / A;
                final double rho = rho0 + R * (lat1 - lat) / RO;
                final double n = sin(lat1 / RO);
                final double Y = rho0 - rho * cos(n * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eqdc coordinate function, ", "G_eqdc > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_eqdc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eqdc coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eqdc coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_eqdc coordinate function, ", "1.0 / A, A = 0:", A);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R / A;
                final double B = rho0 - Yr;
                final double rho = sqrt(Xr * Xr + B * B);

                final double lat = lat1 - (rho -rho0) * RO / R;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eqdc coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_eqdc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eqdc coordinate function, ", "X > MAX_FLOAT: ", Y);
                
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eqdc coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double A = tan(lat1 / RO);
                
                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqdc coordinate function, ", "1.0 / A, A = 0:", A);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0= R / A;
                final double B = rho0 - Yr;
                final double eps = atan2(Xr, B) * RO;
                final double n = sin(lat1 / RO);

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqdc coordinate function, ", "1.0 / n, n = 0:", n);

                final double lonr = eps / n;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eqdc coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Equidistant Conic (true parallel lat1, pole = point)        
        public static double F_eqdc2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat1 - 90) < MAX_ANGULAR_DIFF)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_eqdc2 coordinate function, ", "1 / (90 - lat1), lat1 = 90.", lat1);

                final double rho = R * (90 - lat) / RO;
                final double n = cos(lat1 / RO) / (90 - lat1);
                final double X = rho * sin(n * lonr) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eqdc2 coordinate function, ", "F_eqdc2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eqdc2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat1 - MAX_LAT) < MAX_ANGULAR_DIFF)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_eqdc2 coordinate function, ", "1 / (90 - lat1), lat1 = 90.", lat1);

                final double rho = R * (90 - lat) / RO;
                final double n = cos(lat1 / RO) / (90 - lat1);
                final double Y = -rho * cos(n * lonr) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eqdc2 coordinate function, ", "G_eqdc2 > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FI_eqdc2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eqdc2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eqdc2 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho = sqrt(Xr * Xr + Yr * Yr);

                final double lat = 90 - rho / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eqdc2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_eqdc2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eqdc2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eqdc2 coordinate function, ", "Y > MAX_FLOAT: ", Y);
      
                //Throw exception
                if (abs(lat1 - MAX_LAT) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqdc2 coordinate function, ", "1.0 / (90 - lat1), lat1 = 90.", lat1);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double eps = atan2(Xr, -Yr) * RO;
                final double n = cos(lat1 / RO) / (90 - lat1) * RO;

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqdc2 coordinate function, ", "1.0 / n, n = 0:", n);

                final double lonr = eps / n;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eqdc coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Equidistant Conic (true parallels lat1, lat2)
        public static double F_eqdc3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat1 / RO) - cos(lat2 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_eqdc3 coordinate function, ", "1 / A, A = 0:", A);

                final double n = A / (lat2 - lat1) * RO;
                final double rho = R * ((lat2 / RO * cos(lat1 / RO) - lat1 / RO * cos(lat2 / RO)) / A - lat / RO);
                final double X = rho * sin( n * lonr/RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_eqdc3 coordinate function, ", "F_eqdc3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_eqdc3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat1 / RO) - cos(lat2 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_eqdc3 coordinate function, ", "1 / A, A = 0:", A);

                final double n = A / (lat2 - lat1) * RO;
                final double lat0 = 0.5 * (lat1 + lat2);
                final double rho0 = R * ((lat2 / RO * cos(lat1 / RO) - lat1 / RO * cos(lat2 / RO)) / A - lat0 / RO);
                final double rho = R * ((lat2 / RO * cos(lat1 / RO) - lat1 / RO * cos(lat2 / RO)) / A - lat / RO);
                
                final double Y = rho0 - rho * cos(n * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_eqdc3 coordinate function, ", "G_eqdc3 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_eqdc3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eqdc3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_eqdc3 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double A = cos(lat1 / RO) - cos(lat2 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_eqdc3 coordinate function, ", "1.0 / A, A = 0:", A);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lat0 = 0.5 * (lat1 + lat2);
                final double rho0 = R * ((lat2 / RO * cos(lat1 / RO) - lat1 / RO * cos(lat2 / RO)) / A - lat0 / RO);
                final double B = rho0 - Yr;
                final double rho = sqrt(Xr * Xr + B * B);

                final double lat = lat0 - (rho -rho0) * RO / R;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_eqdc3 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_eqdc3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eqdc3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eqdc3 coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Throw exception
                if (abs(lat1 - MAX_LAT) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqdc2 coordinate function, ", "1.0 / (90 - lat1), lat1 = 90.", lat1);

                //Inverse equation
                final double A = cos(lat1 / RO) - cos(lat2 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqdc3 coordinate function, ", "1.0 / A, A = 0:", A);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lat0 = 0.5 * (lat1 + lat2);
                final double rho0 = R * ((lat2 / RO * cos(lat1 / RO) - lat1 / RO * cos(lat2 / RO)) / A - lat0 / RO);
                final double B = rho0 - Yr;
                final double eps = atan2(Xr, B) * RO;

                final double n = A / (lat2 - lat1) * RO;

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_eqdc3 coordinate function, ", "1.0 / n, n = 0:", n);

                final double lonr = eps / n;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eqdc3 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Fahey
        public static double F_fahey(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat / 2.0 / RO);
                final double B = 1 - pow(A, 2);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in F_fahey coordinate function, ", "B < 0: ", B);

                final double X = R * lonr / RO * cos(lat1 / RO) * sqrt(B) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_fahey coordinate function, ", "F_fahey > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_fahey(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * (1 + cos(lat1 / RO)) * tan(lat / 2.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_fahey coordinate function, ", "G_fahey > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_fahey(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_fahey coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_fahey coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                final double Yr = Y - dy;
                final double lat = 2.0 * atan(Yr / (R * (1.0 + cos(lat1 / RO)))) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_fahey coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_fahey(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_fahey coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_fahey coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_fahey(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double A = tan(lat / 2.0 / RO);
                final double B = 1.0 - pow(A, 2);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in GI_fahey coordinate function, ", "B < 0: ", B);

                final double lonr = ( Xr / (R * cos(lat1 / RO) * sqrt(B))) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eck6 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Foucaut Sine-Tangent
        public static double F_fouc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double p = sqrt(PI);
                final double q = 2.0;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = q / p * R * lonr / RO * cos(lat / RO) * pow((cos(lat / q / RO)), 2) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_fouc coordinate function, ", "F_fouc > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_fouc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double p = sqrt(PI);
                final double q = 2.0;

                final double Y = p * R * tan(lat / q / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_fouc coordinate function, ", "G_fouc > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_fouc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_fouc coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_fouc coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double p = sqrt(PI);
                final double q = 2.0;

                final double lat = q * atan(Yr / (p * R)) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_fouc coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_fouc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_fouc coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_fouc coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_fouc(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double  p = sqrt(PI);
                final double  q = 2.0;
                final double  Xr = X - dx;

                final double lonr = p * Xr / (q * R * cos(lat / RO) * pow((cos(lat / q / RO)), 2)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_fouc coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Foucaut Sinusoidal
        public static double F_fouc_s(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO);
                final double B = sin(lat1 / RO);
                final double C = B + (1 - B) * A;

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_fouc_s coordinate function, ", "1 / C, C = 0:", A);

                final double X = R * lonr / RO * A / C + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_fouc_s coordinate function, ", "F_fouc_s > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_fouc_s(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = sin(lat1 / RO);
                final double Y = R * (A * lat / RO + (1 - A) * sin(lat / RO)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_foucs coordinate function, ", "G_foucs > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_fouc_s(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_fouc_s coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_fouc_s coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat0 = 0.0;

                //Find lat
                FILat fl = new FILat(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILat_fouc_s);
                FILatDer fld = new FILatDer(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILatDer_fouc_s);
                double lat = NewtonRaphson.findRoot(fl::function, fld::function, lat0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
          
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_fouc_s coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }
        
        
        public static double GI_fouc_s(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_fouc_s coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_fouc_s coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_fouc_s(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double n = sin(lat1 / RO);
                final double lonr = Xr * (n + (1 - n) * cos(lat / RO)) / (R * cos(lat / RO)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_fouc_s coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        public static double FILat_fouc_s(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                //F = n * lat + (1 - n) * sin(lat) - Y, n = sin(lat1)
                final double Yr = Y - dy;
                final double n = sin(lat1 / RO);
                final double F = (R * (n * lat_i / RO + (1 - n) * sin(lat_i / RO)) - Yr) * RO;

                return F;
        }


        public static double FILatDer_fouc_s(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                //dF =  n +  (1 - n) * cos(lat), n = sin(lat1)
                final double n = sin(lat1 / RO);
                final double dF = R * (n + (1 - n) * cos(lat_i / RO));

                return dF;
        }


        //Fournier I Globular
        public static double F_fourn(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                double X = 0;
                final double lonr = CartTransformation.redLon0(lon, lon0);

                if ((abs(lonr) < MAX_ANGULAR_DIFF) || (abs(abs(lat) - 90) < MAX_ANGULAR_DIFF))
                {
                        X = dx;
                }

                else if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else if (abs(abs(lonr) - 90) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO * cos(lat / RO) + dx;
                }

                else
                {
                        //Call Y Fournier
                        final double Y = G_fourn(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                        double N = 1 - (2.0 * Y / (PI  * R)) * (2.0 * Y / (PI  * R));

                        //Throw exception
                        if (N < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(N) in F_fourn coordinate function, ", "N < 0: ", N);

                        //Correct N
                        if (N < 0.0) N = 0.0;
                        
                        X = R * lonr / RO  * sqrt(N) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_fourn coordinate function, ", "F_fourn > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_fourn(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                double Y = 0;
                final double lonr = CartTransformation.redLon0(lon, lon0);

                if ((abs(lonr) < MAX_ANGULAR_DIFF) || (abs(abs(lat) - 90) < MAX_ANGULAR_DIFF))
                {
                        Y = R * lat / RO + dy;
                }

                else if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else if (abs(abs(lonr) - 90) < MAX_ANGULAR_DIFF)
                {
                        Y = R * PI / 2.0 * sin(lat / RO) + dy;
                }

                else
                {
                        final double C = PI * PI / 4;
                        final double P = PI * abs(sin(lat / RO));
                        final double Q = P - 2.0 * abs(lat) / RO;

                        // Throw exception
                        if (abs(Q) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_fourn coordinate function, ", "1 / Q, Q = 0.", Q);

                        final double F = ((C - (lat / RO) * (lat / RO)) / Q);
                        final double G = 2.0 * lonr / (PI * RO);
                        final double L = (G * G - 1);

                        // Throw exception
                        if (abs(L) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_fourn coordinate function, ", "1 / L, L = 0.", L);

                        double M = F * F - L * (C - F * P - (lonr / RO) * (lonr / RO));

                        //Throw exception
                        if (M < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(M) in G_fourn coordinate function, ", "M < 0: ", M);

                        //Correct M
                        if (M < 0.0) M = 0.0;
                        
                        Y = R / L  * signum(lat) * (sqrt(M) - F) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_fourn coordinate function, ", "G_fourn > MAX_FLOAT: ", Y);

                return Y;
        }


        //Fournier II Elliptical
        public static double F_fourn2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R / sqrt(PI) * lonr / RO * cos(lat / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_fourn2 coordinate function, ", "F_fourn2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_fourn2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * sqrt(PI) / 2.0 * sin(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_fourn2 coordinate function, ", "G_fourn2 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Gall Stereographic
        public static double F_gall(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * cos(lat1 / RO) * lonr / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_gall coordinate function, ", "F_gall > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_gall(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(lat/2) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat/2) in F_fourn coordinate function, ", "lat/2.0 = +-90: ", lat);

                final double Y = R * (1 + cos(lat1 / RO)) * tan(lat / 2.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_gall coordinate function, ", "G_gall > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_gall(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_gall coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;

                final double lat = 2.0 * atan (Yr /  (R * (1.0 + cos(lat1 / RO)))) * RO;
                        
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_gall coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_gall(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_gall coordinate function, ", "FI_cea > MAX_FLOAT: ", X);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_gall coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double Xr = X - dx;

                final double lonr = Xr / (R *  cos(lat1 / RO)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_gall coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Ginsburg IV
        public static double F_gins4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 1.0;
                final double c2 = 0.0;
                final double c3 = 2.8283;
                final double c4 = -1.698450;
                final double c5 = 0.754046;
                final double c6 = -0.180646;
                final double c7 = 1.760031;
                final double c8 = -0.389143;
                final double c9 = 0.042555;

                final double X = F_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dx);

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_gins4 coordinate function, ", "F_gins8 > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_gins4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 1.0;
                final double c2 = 0.0;
                final double c3 = 2.8283;
                final double c4 = -1.698450;
                final double c5 = 0.754046;
                final double c6 = -0.180646;
                final double c7 = 1.760031;
                final double c8 = -0.389143;
                final double c9 = 0.042555;

                final double Y = G_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dy);
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_gins4 coordinate function, ", "G_gins8 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Ginsburg V
        public static double F_gins5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 1.0;
                final double c2 = 0.0;
                final double c3 = 2.583819;
                final double c4 = -0.835827;
                final double c5 = 0.170354;
                final double c6 = -0.038094;
                final double c7 = 1.543313;
                final double c8 = -0.411435;
                final double c9 = 0.082742;

                final double X = F_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dx);

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_gins5 coordinate function, ", "F_gins8 > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_gins5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 1.0;
                final double c2 = 0.0;
                final double c3 = 2.583819;
                final double c4 = -0.835827;
                final double c5 = 0.170354;
                final double c6 = -0.038094;
                final double c7 = 1.543313;
                final double c8 = -0.411435;
                final double c9 = 0.082742;

                final double Y = G_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dy);
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_gins5 coordinate function, ", "G_gins8 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Ginsburg VI
        public static double F_gins6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 0.994605;
                final double c2 = 0.044707;
                final double c3 = 2.603377;
                final double c4 = -0.622711;
                final double c5 = -0.034239;
                final double c6 = 0.0;
                final double c7 = 1.341985;
                final double c8 = -0.054981;
                final double c9 = 0.0;

                final double X = F_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dx);

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_gins6 coordinate function, ", "F_gins8 > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_gins6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 0.994605;
                final double c2 = 0.044707;
                final double c3 = 2.603377;
                final double c4 = -0.622711;
                final double c5 = -0.034239;
                final double c6 = 0.0;
                final double c7 = 1.341985;
                final double c8 = -0.054981;
                final double c9 = 0.0;

                final double Y = G_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dy);
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_gins6 coordinate function, ", "G_gins8 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Ginsburg IX
        public static double F_gins9(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 1.0;
                final double c2 = 0.0;
                final double c3 = 2.651600;
                final double c4 = -0.765340;
                final double c5 = 0.191230;
                final double c6 = -0.047094;
                final double c7 = 1.362890;
                final double c8 = -0.139650;
                final double c9 = 0.013762;

                final double X = F_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dx);

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_gins9 coordinate function, ", "F_gins8 > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_gins9(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 1.0;
                final double c2 = 0.0;
                final double c3 = 2.651600;
                final double c4 = -0.765340;
                final double c5 = 0.191230;
                final double c6 = -0.047094;
                final double c7 = 1.362890;
                final double c8 = -0.139650;
                final double c9 = 0.013762;

                final double Y = G_gins(lat, lonr, R, c1, c2, c3, c4, c5, c6, c7, c8, c9, dy);
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_gins9 coordinate function, ", "G_gins8 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        //Ginsburg IV-IX equations
        private static double F_gins(final double lat, final double lon, final double R, final double c1, final double c2, final double c3, final double c4, final double c5, final double c6, final double c7, final double c8, final double c9, final double dx)
        {
                double X = 0;
                final double latp2 = lat * lat / (RO * RO);
                final double latp4 = latp2 * latp2;
                final double xa = 0;
                final double ya = c1 * lat / RO + c2 * lat / RO * latp2;
                final double xb = c3 + c4 * latp2 + c5 * latp4 + c6 * latp2 * latp4;
                final double yb = c7 * lat / RO + c8 * lat / RO * latp2 + c9 * lat / RO * latp4;
                final double yba = yb - ya;

                //Point on the equator
                if (abs(yba) < MIN_FLOAT)
                {
                        X = R * lon / RO * c3 / PI + dx;
                }

                else
                {
                        final double m = (xb * xb + yba * yba) / (2.0 * yba);
                        double xbm = xb / m;

                        //Throw exception
                        if (xbm > 1.0 + ARGUMENT_ROUND_ERROR || xbm < -1.0 - ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(xbm) in F_gins coordinate function, ", "abs(xbm) > 1: ", xbm);

                        //Correct xbm
                        if (xbm > 1.0) xbm = 1.0;
                        else if (xbm < -1.0) xbm = -1.0;

                        final double alpha = asin(xbm) * lon / RO / PI;

                        X = R * m * sin(alpha) + dx;
                }

                return X;
        }

        private static double G_gins(final double lat, final double lon, final double R, final double c1, final double c2, final double c3, final double c4, final double c5, final double c6, final double c7, final double c8, final double c9, final double dy)
        {
                double Y = 0;
                final double latp2 = lat * lat / (RO * RO);
                final double latp4 = latp2 * latp2;
                final double xa = 0;
                final double ya = c1 * lat / RO + c2 * lat / RO * latp2;
                final double xb = c3 + c4 * latp2 + c5 * latp4 + c6 * latp2 * latp4;
                final double yb = c7 * lat / RO + c8 * lat / RO * latp2 + c9 * lat / RO * latp4;
                final double yba = yb - ya;

                //Point on the equator
                if (abs(yba) < MIN_FLOAT)
                {
                        Y = dy;
                }

                else
                {
                        final double m = (xb * xb + yba * yba) / (2.0 * yba);
                        double xbm = xb / m;

                        //Throw exception
                        if (xbm > 1.0 + ARGUMENT_ROUND_ERROR || xbm < -1.0 - ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(xbm) in G_gins coordinate function, ", "abs(xbm) > 1: ", xbm);

                        //Correct xbm
                        if (xbm > 1.0) xbm = 1.0;
                        else if (xbm < -1.0) xbm = -1.0;

                        final double alpha = asin(xbm) * lon / RO / PI;
                        Y = R * (ya + m - m * cos(alpha)) + dy;
                }

                return Y;
        }
        
        //Ginsburg VIII
        public static double F_gins8(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 0.162388;
                final double c2 = 0.87;
                final double c3 = 0.000952426;

                final double X = R * lonr / RO * (1.0 - c1 * (lat / RO) * (lat / RO)) * (c2 - c3 * pow((lonr / RO), 4)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_gins8 coordinate function, ", "F_gins8 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_gins8(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO * (1 + 1 / 12 * pow((abs(lat) / RO), 3)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_gins8 coordinate function, ", "G_gins8 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_gins8(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_gins8 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_gins8 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;

                //Y = 0
                double lat = 0;

                //Y <> 0
                if (abs(Yr) > MIN_FLOAT)
                {
                        //Solve quadric equation ax^4 + x - c = 0, where x = - Yr / R
                        final double at = 1.0 / 12;
                        final double ct = -Yr / R;

                        final double p1 = pow(sqrt(3.0 * (27.0 * at * at - 256.0 * at * at * at * ct * ct * ct)) + 9.0 * at, 1.0 / 3);
                        final double p2 = 4.0 * pow(2.0 / 3, 1.0 / 3) * ct;
                        final double p3 = pow(2.0, 1.0 / 3) * pow(3.0, 2.0 / 3) * at;
                        final double p4 = p2 / p1;
                        final double p5 = p1 / p3;
                        final double p6 = p4 + p5;

                        final double D1 = -p6 - 2.0 / (at * sqrt(p6));

                        if (D1 > 0)
                        {
                                final double x1 = 0.5 * sqrt(p6) - 0.5 * sqrt(D1);
                                final double x2 = 0.5 * sqrt(p6) + 0.5 * sqrt(D1);

                                lat = ((abs(x1) < abs(x2)) ? x1 : x2) * RO;
                        }

                        else
                        {
                                final double D2 = -p6 + 2.0 / (at * sqrt(p6));

                                if (D2 > 0)
                                {
                                        final double x3 = -0.5 * sqrt(p6) - 0.5 * sqrt(D2);
                                        final double x4 = -0.5 * sqrt(p6) + 0.5 * sqrt(D2);

                                        lat = ((abs(x3) < abs(x4)) ? x3 : x4) * RO;
                                }
                        }
                }

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_gins8 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_gins8(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_gins8 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_gins8 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_gins8(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                //Inverse equation
                final double lonr0 = 0.0;
                FILon fl = new FILon(lat, X, R, lat1, lat2, lon0 , dx, dy, Projections::FILon_gins8);
                FILonDer fld = new FILonDer(lat, X, R, lat1, lat2, lon0 , dx, dy, Projections::FILonDer_gins8);
                double lonr = NewtonRaphson.findRoot(fl::function, fld::function, lonr0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
        
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_gins8 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        public static double FILon_gins8(final double lon_i, final double lat, final double X, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                // f(lon, Y, R) = cos[(0.95 - |lon| / 12 + |lon^3| / 600)(0.9 * Y / R + 0.03 * Y^5 / R^5)] - X / (R * lon)
                final double Xr = X - dx;

                final double c1 = 0.162388;
                final double c2 = 0.87;
                final double c3 = 0.000952426;

                return R * lon_i / RO * (1 - c1 * lat * lat / (RO * RO)) * (c2 - c3 * pow(lon_i / RO, 4)) - Xr;
        }


        public static double FILonDer_gins8(final double lon_i, final double lat, final double X, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                // df / lon = [sign(lon)/12 - 3 * lon^2 / 600 * sign(lon)] * sin[(0.95 - |lon| / 12 + |lon^3| / 600)(0.9 * Y / R + 0.03 * Y^5 / R^5)] + X / (R * lon^2)
                final double Xr = X - dx;
                
                final double c1 = 0.162388;
                final double c2 = 0.87;
                final double c3 = 0.000952426;

                return (R * (1 - c1 * lat * lat / (RO * RO)) * (c2 - 5.0 * c3 * pow(lon_i / RO, 4))) / RO;        
        }


        //Gnomonic
        public static double F_gnom(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                //if (lat <= 0)
                //        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in F_gnom coordinate function, ", "lat <= 0: ", lat);

                final double rho = R * tan((90 - lat) / RO);
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_gnom coordinate function, ", "F_gnom > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_gnom(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                //if (lat <= 0)
                //        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in G_gnom coordinate function, ", "lat <= 0: ", lat);

                final double rho = R * tan((90 - lat) / RO);
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_gnom coordinate function, ", "G_gnom > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_gnom(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_gnom coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_gnom coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                final double lat = 90 - atan(dist / (2 * R)) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_gnom coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_gnom(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_gnom coordinate function, ", "X > MAX_FLOAT: ", X);
                
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_gnom coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_gnom coordinate function, ", "abs(lonr) > 180.", lonr);
                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        //Goode Homolosine
        public static double F_goode(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lat_int = 40 + 44.0 / 60;
                double X = 0;

                if (abs(lat) <= lat_int)
                {
                        X = F_sinu(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                }

                else
                {
                        X = F_moll(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_goode coordinate function, ", "F_goode > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_goode(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lat_int = 40 + 44.0 / 60;
                double Y = 0;

                if (abs(lat) <= lat_int)
                {
                        Y = G_sinu(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                }

                else
                {
                        final double theta0 = lat;
                        FTheta ft = new FTheta(lat, Projections::FTheta_moll);
                        FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_moll);
                        final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                        Y = R * (sqrt(2) * sin(theta / RO) - 0.0528 * signum(lat)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_goode coordinate function, ", "G_goode > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Guyou
        public static double F_guyou(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                double X = 0;
                
                if ( abs(abs(lat) - 90) < MAX_ANGULAR_DIFF)
                {
                        X = dx;
                }
                
                else
                {
                        final double a = (cos (lat / RO) * sin (lonr / RO) - sin(lat / RO)) / sqrt(2.0); 

                        //Throw exception
                        if (abs(a) > 1.0)
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in F_guyou coordinate function, ", "abs(a) > 1: ", a);

                        final double A = acos(a);
                        final double b = (cos (lat / RO) * sin (lonr / RO) + sin(lat / RO)) / sqrt(2.0); 

                        //Throw exception
                        if (abs(b) > 1.0)
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in F_guyou coordinate function, ", "abs(b) > 1: ", b);

                        final double B = acos(b);
                                                final double u = 0.5 * (A + B);
                        double n = sqrt(2) * cos(u);

                        //Throw exception
                        if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in F_guyou coordinate function, ", "abs(n) > 1: ", n);

                        //Correct n
                        if (n > 1.0) n = 1.0;
                        else if (n < -1.0) n = -1.0;

                        final double N = asin(n);

                        //Compute elliptic integral of the first kind
                        final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                        X = R * Xe + dx; 
                }
                
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_guyou coordinate function, ", "F_guyou > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_guyou(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                double Y = 0;
                
                if ( abs(abs(lat) -90) < MAX_ANGULAR_DIFF)
                {
                        Y = signum(lat) * 1.85407 * R + dy;
                }
    
                else
                {
                        final double a = (cos (lat / RO) * sin (lonr / RO) - sin(lat / RO)) / sqrt(2.0); 

                        //Throw exception
                        if (abs(a) > 1.0)
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in G_guyou coordinate function, ", "abs(a) > 1: ", a);

                        final double A = acos(a);
                        final double b = (cos (lat / RO) * sin (lonr / RO) + sin(lat / RO)) / sqrt(2.0); 

                        //Throw exception
                        if (abs(b) > 1.0)
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in G_guyou coordinate function, ", "abs(b) > 1: ", b);

                        final double B = acos(b);
                        final double  v = 0.5 * (A - B);
                        double m = sqrt(2) * sin(v);

                        //Throw exception
                        if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in G_guyou coordinate function, ", "abs(m) > 1: ", m);

                        //Correct m
                        if (m > 1.0) m = 1.0;
                        else if (m < -1.0) m = -1.0;

                        final double  M = asin(m);
                        
                        //Compute elliptic integral of the first kind
                        final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                        Y = R * Ye + dy; 
                }
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_guyou coordinate function, ", "G_guyou > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Hammer
        public static double F_hammer(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double W = 0.5;
                final double D = 2.0 / (1.0 + cos(lat / RO) * cos(W * lonr / RO));
                final double X = R * sqrt(D) / W * cos(lat / RO) * sin(W * lonr / RO) + dx;
                
                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_hammer coordinate function, ", "F_hammer > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_hammer(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double W = 0.5;
                final double D = 2.0 / (1.0 + cos(lat / RO) * cos(W * lonr / RO));
                final double Y = R * sqrt(D) * sin(lat / RO) + dy;
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_hammer coordinate function, ", "G_hammer > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_hammer(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_hammer coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_hammer coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double A = 16.0 * R * R - 4 * Yr * Yr - Xr * Xr;
                
                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in FI_hammer coordinate function, ", "A < 0: ", A);

                final double B = Yr * sqrt(A) / (4.0 * R * R);
                
                //Throw exception
                if (abs(B) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(B) in FI_hammer coordinate function, ", "abs(B) > 1: ", B);

                final double lat = asin(B) * RO;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_hammer coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_hammer(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_hammer coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_hammer coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double lat = FI_hammer(X, Y, R, lat1, lat2, lon0, dx, dy, c);
                
                final double A = sin(lat / RO);
                final double B = cos(lat / RO);
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double C = 2.0 * R * R * A * A - Yr * Yr;
                double D = Yr * Yr * Yr * Yr * B * B - C * C;
                
                //Throw exception
                if (D < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in GI_hammer coordinate function, ", "D < 0: ", D);

                //Throw exception
                if (D < 0.0)
                        D = 0.0;

                final double lonr = 2.0 * signum (X) * atan2(sqrt(D), C) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_hammer coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Hatano Asymmetrical Equal Area
        public static double F_hataea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double c1 = 0.85;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = 0.5 * lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_hataea);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_hataea);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = c1 * R * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_hataea coordinate function, ", "F_hataea > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_hataea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = (lat < 0 ? 1.93052 : 1.56548);
                
                final double theta0 = 0.5 * lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_hataea);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_hataea);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy *  R * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_hataea coordinate function, ", "G_hataea > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_hataea(final double lat, final double theta)
        {
                final double cp = (lat < 0 ? 2.43763 : 2.67595);
                return 2.0 * theta / RO + sin(2.0 * theta / RO) - cp * sin(lat / RO);
        }


        public static double FThetaDer_hataea(final double lat, final double theta)
        {
                return (2.0 + 2.0 * cos(2.0 * theta / RO)) / RO;
        }

        
        public static double FI_hataea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_hataea coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_hataea coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = (Yr < 0 ? 1.93052 : 1.56548);

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_hataea coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double cp = (Yr < 0 ? 2.43763 : 2.67595);
                double arg2 = (2.0 * theta / RO + sin(2 * theta / RO)) / cp;
                
                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_hataea coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_hataea coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_hataea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_hataea coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_hataea coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_hataea(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double cy = (Yr < 0 ? 1.93052 : 1.56548);
                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_hataea coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double c1 = 0.85;
                final double lonr = Xr / (c1 * R * cos(theta / RO)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_hataea coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //La Hire
        public static double F_hire(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.0 + sqrt(2.0) / 2.0;

                return F_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }


        public static double G_hire(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.0 + sqrt(2.0) / 2.0;

                return G_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }
        
        
        public static double FI_hire(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.0 + sqrt(2.0) / 2.0;

                return FI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }
        
        
        public static double GI_hire(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.0 + sqrt(2.0) / 2.0;

                return GI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }


        //James Perspective
        public static double F_jam(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 1.5;

                return F_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }


        public static double G_jam(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
               final double h = 2.5;

                return G_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }
        
        
        public static double FI_jam(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.0 + sqrt(2.0) / 2.0;

                return FI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }
        
        
        public static double GI_jam(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.0 + sqrt(2.0) / 2.0;

                return GI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }


        //Kavrayskiy V
        public static double F_kav5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double p = 1.50488;
                final double q = 1.35439;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = q / p * R * lonr / RO * cos(lat / RO) / cos(lat / q / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_kav5 coordinate function, ", "F_kav5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_kav5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double p = 1.50488;
                final double q = 1.35439;
                
                final double Y = p * R * sin(lat / q / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_kav5 coordinate function, ", "G_kav5 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_kav5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_kav5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_kav5 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double p = 1.50488;
                final double q = 1.35439;

                double arg =  Yr / (p * R);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in F_kav5 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = q * asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_kav5 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_kav5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_kav5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_kav5 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_qua_aut(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double p = 1.50488;
                final double q = 1.35439;
                final double Xr = X - dx;

                final double lonr = p * Xr / (q * R * cos(lat / RO) / cos(lat / q / RO)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_kav5 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Kavrayskiy VII
        public static double F_kav7(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = sqrt(3.0) / (2.0);
                final double B = 3.0;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (abs(D) == MIN_FLOAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in F_kav7 coordinate function, ", "D = 0: ", D);

                final double X = cx * R * lonr / RO * sqrt(D) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_kav7 coordinate function, ", "F_kav7 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_kav7(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_kav7 coordinate function, ", "G_kav7 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_kav7(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_kav7 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_kav7 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;

                final double lat = Yr / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_kav7 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_kav7(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_kav7 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_kav7 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_kav7(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = sqrt(3.0) / 2.0;
                final double B = 3.0;
                final double Xr = X - dx;
                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in GI_kav7 coordinate function, ", "D < 0: ", D);

                final double lonr = Xr / (R * cx * sqrt(D)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_kav7 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Lambert Azimuthal Equal Area
        public static double F_laea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double rho = 2.0 * R * sin((90 - lat) / 2.0 / RO);
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_laea coordinate function, ", "F_laea > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_laea(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double rho = 2.0 * R * sin((90 - lat) / 2.0 / RO);
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_laea coordinate function, ", "G_laea > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_laea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_laea coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_laea coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                final double A = dist / (2 * R);
                
                //Throw exception
                if (abs(A) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(A) in FI_laea coordinate function, ", "abs(A) > 1: ", A);

                final double lat = 90 - 2 * asin(A) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_laea coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_laea(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_laea coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_laea coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                final double lonr = atan2(Xr, -Yr) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_laea coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Lagrange Conformal
        public static double F_lagrng(final double lat, final double lon, double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if (abs(abs(lat) - MAX_LAT) < MAX_ANGULAR_DIFF) 
                {
                        X = dx;
                }

                else
                {
                        final double W = 2.0;
                        final double A1 = pow((1.0 + sin(lat1 / RO)) / (1.0 - sin(lat1 / RO)), 1.0 / (2.0 * W));
                        final double A = pow((1.0 + sin(lat / RO)) / (1.0 - sin(lat / RO)), 1.0 / (2.0 * W));

                        //Throw exception
                        if (abs(A1) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_lagrng coordinate function, ", "1 / A1, A1 = 0:", A1);

                        final double V = A / A1;
                        final double C = 0.5 * (V + 1.0 / V) + cos(lonr / W / RO);

                        //Throw exception
                        if (abs(C) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_lagrng coordinate function, ", "1 / C, C = 0:", C);

                        X = 2.0 * R * sin(lonr / W / RO) / C + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_lagrng coordinate function, ", "F_larr > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_lagrng (final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                if (abs(abs(lat) - MAX_LAT) < MAX_ANGULAR_DIFF)
                {
                        Y = 2.0 * R * signum(lat) + dy;
                }

                else
                {
                        final double W = 2.0;
                        final double A1 = pow((1.0 + sin(lat1 / RO)) / (1.0 - sin(lat1 / RO)), 1.0 / (2.0 * W));
                        final double A = pow((1.0 + sin(lat / RO)) / (1.0 - sin(lat / RO)), 1.0 / (2.0 * W));

                        //Throw exception
                        if (abs(A1) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_lagrng coordinate function, ", "1 / A1, A1 = 0:", A1);

                        final double V = A / A1;
                        final double C = 0.5 * (V + 1.0 / V) + cos(lonr / W / RO);

                        //Throw exception
                        if (abs(C) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_lagrng coordinate function, ", "1 / C, C = 0:", C);

                        //Throw exception
                        if (abs(V) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_lagrng coordinate function, ", "1 / V, V = 0:", V);

                        Y = R * (V - 1.0 / V) / C + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_lagrng coordinate function, ", "G_lagrng > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_lagrng(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_lagrng coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_lagrng coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                final double arg = Yr / (R * (1.0 + (Xr * Xr + Yr * Yr) / (4.0 * R * R)));
                
                //Throw exception
                if (abs(arg) > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate atanh(arg) in FI_lagrng coordinate function, ", "abs(arg) > 1: ", arg);

                final double mu = log((1.0 + arg ) / (1.0 - arg));
                final double C = ((1.0 + sin(lat1 / RO)) / (1.0 - sin(lat1 / RO)));
                final double lat = 2.0 * atan(sqrt(C) * exp(mu)) * RO - 90.0;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_lagrng coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_lagrng(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_lagrng coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_lagrng coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                final double lonr = 2.0 * atan2(Xr / R, (1.0 - (Xr * Xr + Yr * Yr) / (4.0 * R * R))) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_lagrng coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Larrivee
        public static double F_larr(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO);

                if (A < 0)
                        throw new MathInvalidArgumentException  ("MathInvalidArgumentException: can not evaluate sqrt(A) in F_larr coordinate function, ", "A < 0: ", A);

                final double X = 0.5 * R * lonr / RO * (1 + sqrt(A)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_larr coordinate function, ", "F_larr > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_larr(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / 2.0 / RO) * cos(lonr / 6 / RO);

                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_larr coordinate function, ", "1 / A, A = 0:", A);
                
                final double Y = R * lat / RO / A  + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_larr coordinate function, ", "G_larr > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_larr(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_larr coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_larr coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat0 = 0.0;

                //Find lat
                FILat fl = new FILat(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILat_larr);
                FILatDer fld = new FILatDer(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILatDer_larr);
                double lat = NewtonRaphson.findRoot(fl::function, fld::function, lat0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
          
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_larr coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }
        
        
        public static double GI_larr(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_larr coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_larr coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_larr(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double lonr = 2.0 * Xr  / (R * (1.0 + sqrt(cos(lat / RO)))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_larr coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        public static double FILat_larr(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                //F = Y * cos(lat / 2) * cos(X / (3 * R * (1 + sqrt(cos(lat))))) - R * lat;
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double A = cos(lat_i / RO);

                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in FI_larr coordinate function, ", "A < 0: ", A);

                final double F = (Yr * cos(lat_i / 2 / RO) * cos(Xr / (3.0 * R * (1.0 + sqrt(A)))) - R * lat_i / RO) * RO;

                return F;
        }


        public static double FILatDer_larr(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                //dF =  - Y / 2 * sin(lat/2) * cos(X / (3 * R * (1 + sqrt(cos(lat))))) - 
                //      3 * X * Y * cos(lat/2) * sin(lat) * sin(X / (3 * R *(1 + sqrt(cos(lat))))) / (2 * sqrt(cos(lat)) * (3 * R * (1 + sqrt(cos(lat))))^2) - R
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double A = cos(lat_i / RO);

                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in FI_larr coordinate function, ", "A < 0: ", A);

                final double B = 3.0 * R * (1.0 + sqrt(A));
                final double C = Xr / B;
                final double dF = -Yr / 2.0 * sin(lat_i / 2 / RO) * cos(C) - 3.0 * Xr * Yr * cos(lat_i / 2 / RO) * sin(C) * sin(lat_i / RO) / (2.0 * R * sqrt(A) * B * B) - R;

                return dF;
        }


        //Laskowski
        public static double F_lask(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double a10 = 0.975534;
                final double a12 = -0.119161;
                final double a32 = -0.0143059;
                final double a14 = -0.0547009;

                final double X = R * (a10 * lonr / RO + a12 * (lonr / RO) * (lat / RO) * (lat / RO) + a32 * pow((lonr / RO), 3) * (lat / RO) * (lat / RO) +
                        a14 * (lonr / RO) * pow((lat / RO), 4)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_lask coordinate function, ", "F_lask > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_lask(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double b01 = 1.00384;
                final double b21 = 0.0802894;
                final double b03 = 0.0998909;
                final double b41 = 0.000199025;
                final double b23 = -0.0285500;
                final double b05 = -0.0491032;

                final double Y = R * (b01 * (lat / RO) + b21 * (lonr / RO) * (lonr / RO) * (lat / RO) + b03 * pow((lat / RO), 3) +
                        b41 * pow((lonr / RO), 4) * (lat / RO) + b23 * (lonr / RO) * (lonr / RO) * pow((lat / RO), 3) + b05 * pow((lat / RO), 5)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_lask coordinate function, ", "G_lask > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_lask(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_lask coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_lask coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double a10 = 0.975534;
                final double a12 = -0.119161;
                final double b01 = 1.00384;
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Initial solution
                double lat0 = max(min(Yr / (R * b01) * RO, MAX_LAT - 10.0), MIN_LAT + 10.0);
                double lonr0 = signum(Xr) * 90.0; //Set lonr to the middle of the interval
                
                double [] lat = {lat0};
                double [] lonr = {lonr0};
                
                //Find solution using Gauss-Newton method
                final double fx = GN(Projections::JI_lask, Projections::FRI_lask, Projections::GRI_lask, X, Y, R, lat1, lat2, lon0, dx, dy, c, 1.0e-4, lat, lonr);

                //Throw exception
                if (abs(lat[0]) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_lask coordinate function, ", "abs(lat)  > 90.", lat[0]);

                return lat[0];
        }

         
        public static double GI_lask(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_lask coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_lask coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double a10 = 0.975534;
                final double a12 = -0.119161;
                final double b01 = 1.00384;
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Initial solution
                double lat0 = max(min(Yr / (R * b01) * RO, MAX_LAT - 10.0), MIN_LAT + 10.0);
                double lonr0 = signum(Xr) * 90.0;
                
                double [] lat = {lat0};
                double [] lonr = {lonr0};

                //Find solution using Gauss-Newton method
                final double fx = GN(Projections::JI_lask, Projections::FRI_lask, Projections::GRI_lask, X, Y, R, lat1, lat2, lon0, dx, dy, c, 1.0e-4, lat, lonr);
                
                //Throw exception
                if (abs(lonr[0]) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_lask coordinate function, ", "abs(lonr) > 180.", lonr[0]);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr[0], -lon0);

                return lon;
        }

        
        public static void JI_lask(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c, double [] aj, double [] bj, double [] cj, double [] dj)
        {
                //Jacobian matrix J(2,2) = ( aj bj; cj dj) elements
                final double a10 = 0.975534;
                final double a12 = -0.119161;
                final double a32 = -0.0143059;
                final double a14 = -0.0547009;
                final double b01 = 1.00384;
                final double b21 = 0.0802894;
                final double b03 = 0.0998909;
                final double b41 = 0.000199025;
                final double b23 = -0.0285500;
                final double b05 = -0.0491032;

                //F = rlat = R * (a10 * lon + a12 * lon * lat^2 + a32 * lon^3 * lat^2 + a14 * lon * lat^4) - X;
                //G = rlon = R * (b01 * lat + b21 * lon^2 * lat + b03 * lat^3 + b41 * lon^4 * lat + b23 * lon^2 * lat^3 + b05 * lat^2) - Y 

                //Compute values of the Jacobian matrix J = (a b ; c d)
                // a = j(1, 1) = diff(F, lat)
                // b = j(1, 2) = diff(F, lon)
                aj[0] = R * (2.0 * a12 * lon * lat / (RO * RO) + 2.0 * a32 * lon * lon * lon * lat / (RO * RO * RO * RO) + 4.0 * a14 * lon * lat * lat * lat / (RO * RO * RO * RO));
                bj[0] = R * (a10 + a12 * lat * lat / (RO * RO) + 3.0 * a32 * lon * lon * lat * lat / (RO * RO * RO * RO) + a14 * lat * lat * lat * lat / (RO * RO * RO * RO));

                // c = j(2, 1) = diff(G, lat)
                // d = j(2, 2) = diff(G, lon)
                cj[0] = R * (b01 + b21 * lon * lon / (RO * RO) + 3.0 * b03 * lat * lat / (RO * RO) + b41 * lon * lon * lon * lon / (RO * RO * RO * RO) +
                        3.0 * b23 * lon * lon * lat * lat / (RO * RO * RO * RO) + 5.0 * b05 * lat * lat * lat * lat / (RO * RO * RO * RO));
                dj[0] = R * (2.0 * b21 * lat * lon / (RO * RO) + 4.0 * b41 * lon * lon * lon * lat / (RO * RO * RO * RO) + 2.0 * b23 * lon * lat * lat * lat / (RO * RO * RO * RO));
        }

        
        public static double FRI_lask(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Compute lat residual
                final double a10 = 0.975534;
                final double a12 = -0.119161;
                final double a32 = -0.0143059;
                final double a14 = -0.0547009;
                final double Xr = X - dx;

                //rlat = R * (a10 * lon + a12 * lon * lat^2 + a32 * lon^3 * lat^2 + a14 * lon * lat^4) - X;
                return R * (a10 * lon / RO + a12 * lon * lat * lat / (RO * RO * RO) + a32 * lon * lon * lon * lat * lat / (RO * RO * RO * RO * RO) +
                            a14 * lon * lat * lat * lat * lat / (RO * RO * RO * RO * RO)) - Xr;	
        }

        
        public static double GRI_lask(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Compute lon residual
                final double b01 = 1.00384;
                final double b21 = 0.0802894;
                final double b03 = 0.0998909;
                final double b41 = 0.000199025;
                final double b23 = -0.0285500;
                final double b05 = -0.0491032;
                final double Yr = Y - dy;

                //rlon = R * (b01 * lat + b21 * lon^2 * lat + b03 * lat^3 + b41 * lon^4 * lat + b23 * lon^2 * lat^3 + b05 * lat^5) - Y 
                return R * (b01 * lat / RO + b21 * lon * lon * lat / (RO * RO * RO) + b03 * lat * lat * lat / (RO * RO * RO) + b41 * lon * lon * lon * lon * lat / (RO * RO * RO * RO * RO) +
                            b23 * lon * lon * lat * lat * lat / (RO * RO * RO * RO * RO) + b05 * lat * lat * lat * lat * lat / (RO * RO * RO * RO * RO)) - Yr;
        }


        //Lambert Conformal Conic
        public static double F_lcc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_lcc coordinate function, ", "1.0 / A, A = 0:", A);

                final double n = sin(lat1 / RO);
                final double rho0 = R / A;
                final double B = tan((lat1 / 2.0 + 45) / RO);
                final double C = tan((lat / 2.0 + 45) / RO);
                
                //Throw exception
                if (abs(C) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_lcc coordinate function, ", "C > MAX_FLOAT: ", C);
                
                final double D = B / C;
                
                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate F_lcc coordinate function, ", "pow(D, n), D < 0.", D);
                        
                final double rho = rho0 * pow((D), n);
                final double X = rho * sin(n * lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_lcc coordinate function, ", "F_lcc > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_lcc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_lcc coordinate function, ", "1.0 / A, A = 0:", A);

                final double n = sin(lat1 / RO);
                final double rho0 = R / A;
                final double B = tan((lat1 / 2.0 + 45) / RO);
                final double C = tan((lat / 2.0 + 45) / RO);

                //Throw exception
                if (abs(C) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_lcc coordinate function, ", "C > MAX_FLOAT: ", C);
                
                final double D = B / C;
                
                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate G_lcc coordinate function, ", "pow(D, n), D < 0.", D);
                        
                final double rho = rho0 * pow((D), n);
                final double Y = rho0 - rho * cos(n * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_lcc coordinate function, ", "G_lcc > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_lcc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_lcc coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_lcc coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_lcc coordinate function, ", "1.0 / A, A = 0:", A);

                final double n = sin(lat1 / RO);

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_lcc coordinate function, ", "1.0 / n, n = 0:", n);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R / A;
                final double B = rho0 - Yr;
                final double rho = sqrt(Xr * Xr + B * B);
                
                final double C = rho / rho0;
                final double D = pow(C, 1.0 / n);
                final double E = tan((lat1 / 2.0 + 45) / RO);

                final double lat = 2 * atan2(E, D) * RO - 90;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_lcc coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_lcc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_lcc coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_lcc coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_lcc coordinate function, ", "1.0 / A, A = 0:", A);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R / A;
                final double B = rho0 - Yr;
                final double eps = atan2(Xr, B) * RO;
                final double n = sin(lat1 / RO);

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_lcc coordinate function, ", "1.0 / n, n = 0:", n);

                final double lonr = eps / n;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_lcc coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Lambert Equal Area Conic (standard parallel lat1)
        public static double F_leac(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_leac coordinate function, ", "1 / A, A = 0:", A);

                final double n = sin(lat1 / RO);

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_leac coordinate function, ", "1 / n, n = 0:", n);

                final double rho0 = R / A;
                final double rho = sqrt(rho0 * rho0 + 2.0 * R * R / n * (n - sin(lat / RO)));
                final double X = rho * sin(n * lonr / RO) + dx;
                
                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_leac coordinate function, ", "F_leac > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_leac(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_leac coordinate function, ", "1 / A, A = 0:", A);

                final double n = sin(lat1 / RO);

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate G_leac coordinate function, ", "1 / n, n = 0:", n);

                final double rho0 = R / A;
                final double rho = sqrt(rho0 * rho0 + 2.0 * R * R / n * (n - sin(lat / RO)));
                final double Y = rho0 - rho * cos(n * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_leac coordinate function, ", "G_leac > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_leac(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_leac coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_leac coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_leac coordinate function, ", "1.0 / A, A = 0:", A);

                final double n = sin(lat1 / RO);

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_leac coordinate function, ", "1.0 / n, n = 0:", n);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R / A;
                final double B = rho0 - Yr;
                final double rho = sqrt(Xr * Xr + B * B);
                double C = n - (rho * rho - rho0 * rho0) * n / (2 * R * R);

                //Throw exception
                if (C > 1.0 + ARGUMENT_ROUND_ERROR || C < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(C) in FI_leac coordinate function, ", "abs(C) > 1: ", C);

                //Correct C
                if (C > 1.0) C = 1.0;
                else if (C < -1.0) C = -1.0;

                final double lat = asin(C) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_leac coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_leac(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_leac coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_leac coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_leac coordinate function, ", "1.0 / A, A = 0:", A);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R / A;
                final double B = rho0 - Yr;
                final double eps = atan2(Xr, B) * RO;
                final double n = sin(lat1 / RO);

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_leac coordinate function, ", "1.0 / n, n = 0:", n);

                final double lonr = eps / n;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_leac coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //Lambert Equal Area Conic (standard parallels lat1, lat2)
        public static double F_leac2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double n = (1.0 + sin(lat1 / RO)) / 2.0;

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_leac2 coordinate function, ", "1.0 / n, n = 0:", n);

                final double B = 2.0 / n * (1.0 - sin(lat / RO));

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in F_leac2 coordinate function, ", "A < 0: ", B);

                final double rho = R * sqrt(B);

                final double X = rho * sin(n * lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_leac2 coordinate function, ", "F_leac2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_leac2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double n = (1.0 + sin(lat1 / RO)) / 2.0;

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_leac2 coordinate function, ", "1.0 / n, n = 0:", n);

                final double B = 2.0 / n * (1.0 - sin(lat / RO));

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in G_leac2 coordinate function, ", "B < 0: ", B);

                final double rho = R * sqrt(B);
                final double C = 2.0 / n * (1.0 - sin(lat1 / RO));

                //Throw exception
                if (C < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(C) in G_leac2 coordinate function, ", "C < 0: ", C);

                final double rho0 = R * sqrt(C);

                final double Y = rho0 - rho * cos(n * lonr / RO) + dy;
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_leac2 coordinate function, ", "G_leac2 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_leac2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_leac2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_leac2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double n = (1.0 + sin(lat1 / RO)) / 2.0;

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FI_leac2 coordinate function, ", "1.0 / n, n = 0:", n);

                final double C = 2.0 / n * (1.0 - sin(lat1 / RO));

                //Throw exception
                if (C < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(C) in FI_leac2 coordinate function, ", "C < 0: ", C);

                final double Xr = X - dx;
                final double Yr = Y - dy; 
                final double rho0 = R * sqrt(C);
                final double B = rho0 - Yr;
                final double rho = sqrt(Xr * Xr + B * B);
                double D = 1 - n * rho * rho / (2 * R * R);

                //Throw exception
                if (D > 1.0 + ARGUMENT_ROUND_ERROR || D < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(D) in FI_leac2 coordinate function, ", "abs(D) > 1: ", D);

                //Correct D
                if (D > 1.0) D = 1.0;
                else if (D < -1.0) D = -1.0;

                final double lat = asin(D) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_leac coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_leac2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_leac2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_leac2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double n = (1.0 + sin(lat1 / RO)) / 2.0;

                //Throw exception
                if (abs(n) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_leac2 coordinate function, ", "1.0 / n, n = 0:", n);

                final double C = 2.0 / n * (1.0 - sin(lat1 / RO));

                //Throw exception
                if (C < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(C) in G_leac2 coordinate function, ", "C < 0: ", C);
                
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double rho0 = R * sqrt(C);
                final double B = rho0 - Yr;
                final double eps = atan2(Xr, B) * RO;

                final double lonr = eps / n;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_leac coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
         
          
        //Littrow
        public static double F_litt(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in F_litt coordinate function, ", "lat = +-90: ", lat);
                
                final double X = R * sin(lonr / RO) / cos(lat / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_litt coordinate function, ", "F_litt > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_litt(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in G_litt coordinate function, ", "lat = +-90: ", lat);
                
                //Correct lonr > 90
                if (lonr > 90)
                        lonr = 180 - lonr;
                
                //Correct lonr < -90
                else if (lonr < -90)
                        lonr = -180 - lonr;
                
                final double Y = R * tan(lat / RO) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_litt coordinate function, ", "G_litt > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
         public static double FI_litt(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_litt coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_litt coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double aq = Xr * Xr;
                final double bq = R * R + Yr * Yr - Xr * Xr;
                final double cq = -Yr * Yr;

                double t = 0;
                //Linear equation: b * t + c = 0
                if (abs(aq) < MIN_FLOAT)
                {
                        t = -cq / bq;
                }
                //Quadratic equation
                else
                {
                        final double discr = bq * bq - 4.0 * aq * cq;
                        final double t1 = (-bq + sqrt(discr)) / (2.0 * aq);
                        final double t2 = (-bq - sqrt(discr)) / (2.0 * aq);
                        t = (t1 >= 0) && (t1 <= 1) ? t1 : t2;
                }

                //Throw exception
                if (t < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(t) in FI_litt coordinate function, ", "t < 0: ", t);

                final double arg = sqrt(t);

                //Throw exception
                if (arg > 1)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(arg) in FI_litt coordinate function, ", "abs(arg) > 1: ", t);

                final double lat = signum(Yr) * asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_litt coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_litt(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_litt coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_litt coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double lat = FI_litt(X, Y, R, lat1, lat2, lon0, dx, dy, c);
                final double lonr = atan(Xr / Yr * sin(lat / RO)) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_littcoordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Loximuthal
        public static double F_loxim(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                double X = 0;
                final double dlat = lat - lat1;
                
                if (abs(dlat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO * cos(lat1 / RO) + dx;
                }

                else
                {
                        final double A = 0.5 * lat + 45;
                        final double B = 0.5 * lat1 + 45;

                        if ((abs(A) < MAX_ANGULAR_DIFF) || (abs(abs(A) - 90) < MAX_ANGULAR_DIFF))
                        {
                                X = dx;
                        }

                        else
                        {
                                X = R * lonr / RO * dlat / RO / (log(tan(A / RO)) - log(tan(B / RO))) + dx;
                        }
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_loxim coordinate function, ", "F_loxim > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_loxim(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double  Y = R * (lat - lat1) / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_loxim coordinate function, ", "G_loxim > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_loxim(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_loxim coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_loxim coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                
                final double lat = lat1 + Yr / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_loxim coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_loxim(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_loxim coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_loxim coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                double lonr = 0.0;
                final double lat = FI_loxim(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double dlat = lat - lat1;

                if (abs(dlat) < MAX_ANGULAR_DIFF)
                {
                        lonr = Xr / (R * cos(lat1 / RO)) * RO;
                }

                final double A = 0.5 * lat + 45.0;
                final double B = 0.5 * lat1 + 45.0;

                lonr = Xr * (log(tan(A / RO)) - log(tan(B / RO))) / (R * dlat / RO) * RO;

                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_loxim coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Maurer
        public static double F_maurer(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr / RO * (c - 2.0 * lat /RO) / PI + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_maurer coordinate function, ", "F_maurer > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_maurer(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_maurer coordinate function, ", "G_maurer > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_maurer(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_maurer coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_maurer coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                
                final double lat = Yr / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_maurer coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_maurer(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_loxim coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_loxim coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_maurer(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double lonr = Xr * PI / (R * (c - 2.0 * lat /RO)) * RO;

                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_maurer coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //McBryde-Thomas Sine I.
        public static double F_mbt_s(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double p = 1.48875;
                final double q = 1.36509;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = q / p * R * lonr / RO * cos(lat / RO) / cos(lat / q / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_mbt_s coordinate function, ", "F_mbt_s > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_mbt_s(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double p = 1.48875;
                final double q = 1.36509;
                
                final double Y = p * R * sin(lat / q / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_mbt_s coordinate function, ", "G_mbt_s > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_mbt_s(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbt_s coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbt_s coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double p = 1.48875;
                final double q = 1.36509;

                double arg =  Yr / (p * R);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in F_mbt_s coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = q * asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_mbt_s coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_mbt_s(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbt_s coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbt_s coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_qua_aut(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double p = 1.48875;
                final double q = 1.36509;
                final double Xr = X - dx;

                final double lonr = p * Xr / (q * R * cos(lat / RO) / cos(lat / q / RO)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_mbt_s coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //McBryde-Thomas Flat-Pole Sine III
        public static double F_mbt_s3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)        
        {
                final double lat_int = 55 + 51.0 / 60;
                double X = 0;

                if (abs(lat) <= lat_int)
                {
                        X = F_sinu(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                }

                else
                {
                        X = F_mbtfps2(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_mbt_s3 coordinate function, ", "F_mbt_s3 > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_mbt_s3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)        
        {
                final double lat_int = 55 + 51.0 / 60;
                double Y = 0;
                
                if (abs(lat) <= lat_int)
                {
                        Y = G_sinu(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                }

                else
                {
                        final double theta0 = lat;
                        FTheta ft = new FTheta(lat, Projections::FTheta_mbtfps2);
                        FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfps2);
                        final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                        Y = R * (sqrt(6 / (4.0 + PI)) * theta / RO - 0.069065 * signum(lat)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_mbt_s3 coordinate function, ", "G_mbt_s3 > MAX_FLOAT: ", Y);

                return Y;
        }


        //McBryde-Thomas Flat-Pole Parabolic
        public static double F_mbtfpp(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = sqrt(6.0 / 7.0) / 3.0;
                final double A = 2.0;

                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfpp);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfpp);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double B = cos(theta / 3.0 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_mbtfpp coordinate function, ", "1.0 / B, B = 0:", B);

                final double X = cx * R * lonr / RO * (1.0 + A * cos(theta / RO) / B) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_mbtfpp coordinate function, ", "X > MAX_FLOAT: ", X);

                return X;
        }
        
        
        public static double G_mbtfpp(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 3.0 * sqrt(6.0 / 7.0);
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfpp);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfpp);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy * R * sin(theta / 3.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_mbtfpp coordinate function, ", "G_mbtfpq > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FTheta_mbtfpp(final double lat, final double theta)
        {
                final double A = 1.125;
                final double B = 0.4375;
                final double C = sin(theta / 3.0 / RO);

                return A * C - C * C * C  - B * sin(lat / RO);
        }


        public static double FThetaDer_mbtfpp(final double lat, final double theta)
        {
                final double A = 0.3750;
                final double B = sin(theta / 3.0 / RO);

                return (A - B * B) * cos(theta / 3.0 / RO) / RO;
        }
        
        
        public static double FI_mbtfpp(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbtfpp coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbtfpp coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 3.0 * sqrt(6.0 / 7.0);

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_mbtfpp coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double  theta = 3.0 * asin(arg) * RO;

                final double  A = 1.125;
                final double  B = 0.4375;
                final double  C = sin(theta / 3.0 / RO);

                double arg2 = (A * C - C * C * C) / B;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_mbtfpp coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double  lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_mbtfpp coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_mbtfpp(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbtfpp coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbtfpp coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double cx = sqrt(6.0 / 7.0) /3.0;
                final double cy = 3.0 * sqrt(6.0 / 7.0);
                final double A = 2.0;
                final double Xr = X - dx;
                final double Yr = Y - dy;

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_mbtfpp coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = 3.0 * asin(arg) * RO;
                final double B = cos(theta / 3.0 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_mbtfpp coordinate function, ", "1.0 / B, B = 0:", B);

                final double lonr = Xr / (cx * R * (1.0 + A * cos(theta / RO) / B)) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_mbtfpp coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //McBryde-Thomas Flat-Pole Quartic
        public static double F_mbtfpq(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 1.0 / sqrt(3.0 * sqrt(2.0) + 6.0);
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfpq);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfpq);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = cx * R * lonr / RO * (1.0 + 2.0 * cos(theta / RO) / cos(0.5 * theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_mbtfpq coordinate function, ", "F_mbtfpq > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_mbtfpq(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 2.0 * sqrt(3.0) / sqrt(2.0 + sqrt(2.0));
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfpq);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfpq);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy * R * sin(theta / 2.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_mbtfpq coordinate function, ", "G_mbtfpq > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_mbtfpq(final double lat, final double theta)
        {
                final double A = (1.0 + sqrt(2.0) / 2.0);
                
                return sin(theta / 2.0 / RO) + sin(theta / RO) - A * sin(lat / RO);
        }


        public static double FThetaDer_mbtfpq(final double lat, final double theta)
        {
                return ( 0.5 * cos(theta / 2.0 / RO) + cos(theta / RO)) / RO;
        }
        
        
        public static double FI_mbtfpq(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbtfpq coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbtfpq coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 2.0 * sqrt(3.0) / sqrt(2.0 + sqrt(2.0));

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_mbtfpq coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = 2.0 * asin(arg) * RO;

                final double A = (1.0 + sqrt(2.0) / 2.0);

                double arg2 = (sin(theta / 2.0 / RO) + sin(theta / RO)) / A;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_mbtfpq coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_mbtfpq coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_mbtfpq(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbtfpq coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbtfpq coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double cx = 1.0 / sqrt(3.0 * sqrt(2.0) + 6.0);
                final double cy = 2.0 * sqrt(3.0) / sqrt(2.0 + sqrt(2.0));
                final double Xr = X - dx;
                final double Yr = Y - dy;

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_mbtfpq coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = 2.0 * asin(arg) * RO;

                //Throw exception
                if (abs(theta) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_mbtfpq coordinate function, ", "1.0 / theta, theta = 0:", theta);

                final double lonr = Xr / (cx * R * (1.0 + 2.0 * cos(theta / RO) / cos(0.5 * theta / RO))) * RO;

                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_mbtfpq coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //McBryde-Thomas Flat-Pole Sine I
        public static double F_mbtfps(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 0.22248;
                final double D = 1.36509;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfps);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfps);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = cx * R * lonr / RO * (1.0 + 3.0 * cos(theta / RO) / cos(theta / D / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_mbtfps coordinate function, ", "F_mbtfps > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_mbtfps(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 1.44492;
                final double D = 1.36509;
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfps);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfps);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy * R * sin(theta / D / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_mbtfs coordinate function, ", "G_mbtfps > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_mbtfps(final double lat, final double theta)
        {
                final double B = 0.45503;
                final double C = 1.41546;
                final double D = 1.36509;

                return B * sin(theta / D / RO) + sin(theta / RO) - C * sin(lat / RO);
        }


        public static double FThetaDer_mbtfps(final double lat, final double theta)
        {
                final double B = 0.45503;
                final double D = 1.36509;

                return (B / D * cos(theta / D / RO) + cos(theta / RO)) / RO;
        }
        
        
        public static double FI_mbtfps(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbtfps coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mbtfps coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 1.44492;
                final double D = 1.36509;

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_mbtfps coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = D * asin(arg) * RO;

                final double B = 0.45503;
                final double C = 1.41546;

                double arg2 = (B * sin(theta / D / RO) + sin(theta / RO)) / C;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_mbtfps coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_mbtfps coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_mbtfps(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbtfps coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mbtfps coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double cx = 0.22248;
                final double cy = 1.44492;
                final double D = 1.36509;
                final double Xr = X - dx;
                final double Yr = Y - dy;

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_mbtfps coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = D * asin(arg) * RO;

                //Throw exception
                if (abs(theta) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_mbtfps coordinate function, ", "1.0 / theta, theta = 0:", theta);

                final double lonr = Xr / (cx * R * (1.0 + 3.0 * cos(theta / RO) / cos(theta / D / RO))) * RO;

                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_mbtfps coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //McBryde-Thomas Flat-Pole Sine II
        public static double F_mbtfps2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfps2);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfps2);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = R * sqrt(6 / (4.0 + PI)) * (0.5 + cos(theta / RO)) * lonr / RO / 1.5 + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_mbtfps coordinate function, ", "F_mbtfps > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_mbtfps2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_mbtfps2);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_mbtfps2);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = R * sqrt(6 / (4.0 + PI)) * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_mbtfs coordinate function, ", "G_mbtfps > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_mbtfps2(final double lat, final double theta)
        {
                return theta / 2.0 / RO + sin(theta / RO) - (1 + PI / 4) * sin(lat / RO);
        }


        public static double FThetaDer_mbtfps2(final double lat, final double theta)
        {
                return ( 0.5 + cos(theta / RO)) / RO;
        }


        //Mercator
        public static double F_merc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_merc coordinate function, ", "F_merc > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_merc(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = tan((lat / 2.0 + 45) / RO);

                //Throw exception
                if (abs(lat) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in G_merc coordinate function, ", "lat = +-90: ", lat);

                //Throw exception
                if (A <= 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate log(A) in G_merc coordinate function, ", "A <= 0: ", A);

                final double Y = R * log(A) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_merc coordinate function, ", "G_merc > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_merc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_merc coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = 90 - 2 * atan(exp( -(Y - dy) / R)) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_merc coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_merc(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_merc coordinate function, ", "X > MAX_FLOAT: ", X);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_merc coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double lonr = (X - dx) / (R * cos(lat1 / RO)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_merc coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Miller Cylindrical
        public static double F_mill(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_mill coordinate function, ", "F_mill > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_mill(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = tan((0.4 * lat + 45) / RO);

                //Throw exception
                if (A <= 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate log(A) in G_mill coordinate function, ", "A <= 0: ", A);

                final double Y = R * log(A) / 0.8 + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_mill coordinate function, ", "G_mill > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_mill(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_mill coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = 2.5 * (atan(exp(0.8 * (Y - dy) / R)) - 45 / RO ) *RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_mill coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_mill(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_mill coordinate function, ", "X > MAX_FLOAT: ", X);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_mill coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double lonr = (X - dx) / (R * cos(lat1 / RO)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_mill coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //Mollweide
        public static double F_moll(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double r = sqrt(2.0);
                final double c1 = 2.0 * r / PI;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_moll);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_moll);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = c1 * R * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_moll coordinate function, ", "F_moll > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_moll(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double c2 = sqrt(2.0);
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_moll);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_moll);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = c2 * R * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_moll coordinate function, ", "G_moll > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_moll(final double lat, final double theta)
        {
                final double cp = PI;
                return 2.0 * theta / RO + sin(2.0 * theta / RO) - cp * sin(lat / RO);
        }


        public static double FThetaDer_moll(final double lat, final double theta)
        {
                return ( 2.0 + 2.0 * cos(2.0 * theta / RO)) / RO;
        }
        
        
        public static double FI_moll(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_moll coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_moll coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                 final double c2 = sqrt(2.0);
                
                double arg = Yr / (R * c2);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_moll coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double cp = PI;
                final double theta = asin(arg) * RO;
                double arg2 = (2 * theta / RO + sin(2 * theta / RO)) / cp;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_moll coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_moll coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_moll(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_moll coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_moll coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_moll(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                double arg = Yr / (R * sqrt(2));

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_moll coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;
                
                final double r = sqrt(2.0);
                final double c1 = 2.0 * r / PI;
                final double lonr = Xr / (c1 * R * cos(theta / RO)) * RO;

                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_moll coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Nell
        public static double F_nell(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_nell);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_nell);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = R * 0.5 * lonr / RO * (1 + cos(theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_nell coordinate function, ", "F_nell > MAX_FLOAT: ", X);

                return X;
        }

        public static double G_nell(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_nell);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_nell);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = R * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_nell coordinate function, ", "G_nell > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FTheta_nell(final double lat, final double theta)
        {
                return  theta / RO + sin(theta / RO) - 2.0 * sin(lat / RO);
        }


        public static double FThetaDer_nell(final double lat, final double theta)
        {
                return (1 + cos(theta / RO)) / RO;
        }
        
        
        public static double FI_nell(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nell coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nell coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;

                final double theta = Yr / R * RO;
                double arg = 0.5 * (theta / RO + sin(theta / RO));
                
                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_nell coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_nell coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_nell(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nell coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nell coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double theta = Yr / R * RO;

                final double lonr = 2.0 * Xr / (R * (1.0 + cos(theta / RO))) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_nell coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
       
        
        //Nell-Hammer
        public static double F_nell_h(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 0.5 * R * lonr / RO * (1 + cos(lat / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_nell_h coordinate function, ", "F_nell_h > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_nell_h(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * R * (lat / RO - tan(lat / 2.0 / RO)) + dy;

                //Throw exception
                if (abs(abs(lat) - 2.0 * MAX_LAT) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in F_nell_h coordinate function, ", "lat = +-180: ", lat);

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_nell_h coordinate function, ", "G_nell_h > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_nell_h(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nell_h coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nell_h coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                FILat fl = new FILat(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILat_nell_h);
                FILatDer fld = new FILatDer(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILatDer_nell_h);
                double lat = NewtonRaphson.findRoot(fl::function, fld::function, lat1, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_nell_h coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_nell_h(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nell_h coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nell_h coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_nell_h(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;

                final double lonr = 2.0 * Xr / (R * (1.0 + cos(lat / RO))) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_nell_h coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        public static double FILat_nell_h(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                // f(lat, Y, R) = lat - tan(0.5 * lat) - Y / (2 * R)
                final double Yr = Y - dy;
                return lat_i / RO - tan(0.5 * lat_i / RO) - Yr / (2 * R);
        }


        public static double FILatDer_nell_h(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                // df / dtheta = 1 - 1.0 / (1 + cos(lat))
                return (1.0 - 1.0 / (1.0 + cos(lat_i / RO))) / RO;     
        }

        //Nicolosi Globular
        /*
        public static double F_nicol(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if ((abs(lonr) < MAX_ANGULAR_DIFF) || ( abs(abs(lat) - 90) < MAX_ANGULAR_DIFF))
                {
                        X = dx;
                }

                else if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else if ( abs(abs(lonr) - 90) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO * cos(lat / RO) + dx;
                }

                else
                {
                        final double b = PI / (2.0 * lonr / RO) - 2.0 * lonr / RO / PI;
                        final double cc = 2.0 * lat / RO / PI;
                        final double dd = sin(lat / RO) - cc;

                        //Throw exception
                        if (abs(dd) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_nicol coordinate function, ", "1.0 / dd, dd = 0:", dd);

                        final double d = (1.0 - cc * cc) / dd;

                        //Throw exception
                        if (abs(d) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_nicol coordinate function, ", "1.0 / d, d = 0:", d);

                        final double fx = 1.0 + b * b / (d * d);
                        final double g = cos(lat / RO);

                        //Throw exception
                        if (abs(fx) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_nicol coordinate function, ", "1.0 / fx, fx = 0:", fx);

                        final double m = (b * sin(lat / RO) / d - b / 2) / fx;
                        double p = m * m + g * g / fx;

                        //Throw exception
                        if (p < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(p) in F_nicol coordinate function, ", "p < 0: ", p);

                        //Correct p
                        if (p < 0.0) p = 0.0;

                        X = R * PI / 2.0 * (m + signum(lonr) * sqrt(p)) + dx;   
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_nicol coordinate function, ", "F_nicol > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_nicol(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0; 

                if ((abs(lonr) < MAX_ANGULAR_DIFF) || ( abs(abs(lat) -90) < MAX_ANGULAR_DIFF))
                {
                        Y = R * lat / RO + dy;
                }

                else if ( abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else if ( abs(abs(lonr) - 90) < MAX_ANGULAR_DIFF)
                {
                        Y = R * PI / 2.0 * sin(lat / RO) + dy;
                }

                else
                {
                        final double b = PI / (2.0 * lonr / RO) - 2.0 * lonr / RO / PI;

                        //Throw exception
                        if (abs(b) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_nicol coordinate function, ", "1.0 / b, b = 0:", b);

                        final double cc = 2.0 * lat / RO / PI;
                        final double dd = sin(lat / RO) - cc;

                        //Throw exception
                        if (abs(dd) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_nicol coordinate function, ", "1.0 / dd, dd = 0:", dd);

                        final double d = (1.0 - cc * cc) / dd;
                        final double fy = 1.0 + d * d / (b * b);

                        //Throw exception
                        if (abs(fy) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_nicol coordinate function, ", "1.0 / fy, fy = 0:", fy);

                        final double g = sin(lat / RO);
                        final double n = (d * d * g / (b * b) + d / 2.0) / fy;

                        double q = n * n - (d * d * g * g / (b * b) + d * g - 1.0) / fy;

                        //Throw exception
                        if (q < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(q) in G_nicol coordinate function, ", "q < q: ", q);

                        //Correct q
                        if (q < 0.0) q = 0.0;

                        Y = R * PI / 2.0 * (n - signum(lat) * sqrt(q)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_nicol coordinate function, ", "G_nicol > MAX_FLOAT: ", Y);

                return Y;
        }
        
                
        public static double FI_nicol(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nicol coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nicol coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the prime meridian
                if (abs(Xr) < MIN_FLOAT)
                        return Yr / R * RO;

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return 0.0;

                //Otherwise, find initial solution
                double lat0 = max(min(Yr / R * RO, MAX_LAT - 10.0), MIN_LAT + 10.0);
                double lonr0 = max(min((PI * PI * R * Xr) / (PI * PI * R * R - 4.0 * Yr * Yr) * RO, MAX_LAT - 10.0), MIN_LAT + 10.0);

                double [] lat = {lat0};
                double [] lonr = {lonr0};
                
                //Find solution using Gauss-Newton method
                final double fx = GN(Projections::JI_nicol, Projections::FRI_nicol, Projections::GRI_nicol, X, Y, R, lat1, lat2, lon0, dx, dy, c, 1.0e-4, lat, lonr);

                //Throw exception
                if (abs(lat[0]) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_nicol coordinate function, ", "abs(lat)  > 90.", lat[0]);

                return lat[0];
        }

         
        public static double GI_nicol(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nicol coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nicol coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the central meridian
                if (abs(Xr) < MIN_FLOAT)
                        return 0.0;

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return Xr / R * RO;

                //Otherwise, initial solution
                double lat0 = max(min(Yr / R * RO, MAX_LAT - 10.0), MIN_LAT + 10.0);
                double lonr0 = max(min((PI * PI * R * Xr) / (PI * PI * R * R - 4.0 * Yr * Yr) * RO, MAX_LAT - 10.0), MIN_LAT + 10.0);
    
                double [] lat = {lat0};
                double [] lonr = {lonr0};

                //Find solution using Gauss-Newton method
                final double fx = GN(Projections::JI_nicol, Projections::FRI_nicol, Projections::GRI_nicol, X, Y, R, lat1, lat2, lon0, dx, dy, c, 1.0e-4, lat, lonr);
                
                //Throw exception
                if (abs(lonr[0]) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_nicol coordinate function, ", "abs(lonr) > 180.", lonr[0]);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr[0], -lon0);

                return lon;
        }
        
        
        public static void JI_nicol(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c, double [] aj, double [] bj, double [] cj, double [] dj)
        {
                //Jacobian matrix J(2,2) = ( aj bj; cj dj) elements
                if ((abs(abs(lat) - 90) < MAX_ANGULAR_DIFF) || abs(lat) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / lat, lat = 0:", lat);

                if ((abs(lon) < MAX_ANGULAR_DIFF) || abs(abs(lon) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / lon, lon = 0:", lon);

                final double b = PI / (2.0 * lon / RO) - 2.0 * lon / RO / PI;

                //Throw exception
                if (abs(b) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / b, b = 0:", b);

                final double cc = 2.0 * lat / RO / PI;
                final double dd = sin(lat / RO) - cc;

                //Throw exception
                if (abs(dd) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / dd, dd = 0:", dd);

                final double d = (1.0 - cc * cc) / dd;
                final double fx = 1.0 + b * b / (d * d);
                final double fy = 1.0 + d * d / (b * b);

                //Throw exception
                if (abs(fx) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / fx, fx = 0:", fx);

                if (abs(fx) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / fy, fy = 0:", fy);

                final double g = sin(lat / RO);
                final double h = cos(lat / RO);

                final double m = (b * g / d - b / 2.0) / fx;
                final double n = (d * d * g / (b * b) + d / 2.0) / fy;

                final double A = 1.0 / (b * b + d * d);
                final double B = d * d * h * h * A;
                final double C = sqrt(B + m * m);
                final double D = n * n + A * (b * b - d * g * (b * b + d * g));
                final double E = b * b + 2.0 * d * g;
                final double F = 3.0 - cos(2.0 * lat / RO) - d * g;

                //Derivatives according to lat/lon
                final double blon = -PI / (2.0 * lon * lon / (RO * RO)) - 2 / PI;
                final double G = 2.0 * lat / RO - PI * g;
                final double H = PI * G * G;

                //Throw exception
                if (abs(H) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / H, H = 0:", H);

                final double dlat = (PI * (h * (4.0 * lat * lat / (RO * RO) - PI * PI) + 2.0 * PI) + 8.0 * lat / RO * (lat / RO - PI * g)) / H;
                final double mlat = b * d * A * h + b * A * A * dlat * ((b * b - d * d) * g - b * b * d);
                final double mlon = -0.5 * d * A * A * blon * (d * d - b * b) * (d - 2.0 * g);
                final double nlat = 0.5 * (2.0 * d * d * h * A + b * b * dlat * A * A * (b * b - d * d + 4.0 * d * g));
                final double nlon = b * blon * d * d * A * A * (d - 2.0 * g);

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Jacobian matrix JI_nicol, ", "1.0 / C, C = 0:", C);

                if (D < MIN_FLOAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in Jacobian matrix JI_nicol, ", "D < 0: ", D);

                //Compute values of the Jacobian matrix J = (a b ; c d)
                // a = j(1, 1) = diff(F, lat)
                // b = j(1, 2) = diff(F, lon)
                aj[0] = R * PI / 2.0 * (mlat + signum(lon) * ((2.0 * b * b * d * dlat * h * h) * A * A - d * d * sin(2.0 * lat / RO) * A + 2.0 * m * mlat) / (2.0 * C));
                bj[0] = R * PI / 2.0 * (mlon + signum(lon) * (2.0 * m * mlon - 2.0 * b * blon * A * B) / (2.0 * C));

                // c = j(2, 1) = diff(G, lat)
                // d = j(2, 2) = diff(G, lon)
                cj[0] = R * PI / 2.0 * (nlat + signum(lat) * (d * A * E * h + b * b * dlat * A * A * (d * F + b * b * g) - 2.0 * n * nlat) / (2.0 * sqrt(D)));
                dj[0] = R * PI / 2.0 * (nlon + signum(lat) * (A * A * b * blon * d * d * (d * g - F) - 2.0 * n * nlon) / (2.0 * sqrt(D)));
        }

        
        public static double FRI_nicol(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Compute lat residual
                if ((abs(abs(lat) - 90) < MAX_ANGULAR_DIFF) || abs(lat) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FRI_nicol coordinate function, ", "1.0 / lat, lat = 0:", lat);

                if ((abs(lon) < MAX_ANGULAR_DIFF) || abs(abs(lon) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FRI_nicol coordinate function, ", "1.0 / lon, lon = 0:", lon);

                //Inverse equations
                final double Xr = X - dx;

                final double b = PI / (2.0 * lon / RO) - 2.0 * lon / RO / PI;
                final double cc = 2.0 * lat / RO / PI;
                final double dd = sin(lat / RO) - cc;

                //Throw exception
                if (abs(dd) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FRI_nicol coordinate function, ", "1.0 / dd, dd = 0:", dd);

                final double d = (1.0 - cc * cc) / dd;
                final double fx = 1.0 + b * b / (d * d);

                //Throw exception
                if (abs(fx) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate FRI_nicol coordinate function, ", "1.0 / fx, fx = 0:", fx);

                final double g = cos(lat / RO);

                final double m = (b * sin(lat / RO) / d - b / 2) / fx;
                double p = m * m + g * g / fx;

                //Throw exception
                if (p < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(p) in FRI_nicol coordinate function, ", "p < 0: ", p);

                //Correct p
                if (p < 0.0) p = 0.0;

                //rlat = R * M_PI / 2.0 * (m + sign(lon) * sqrt(p)) - X;
                return R * PI / 2.0 * (m + signum(lon) * sqrt(p)) - Xr;
        }

        
        public static double GRI_nicol(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Compute lon residual
                if ((abs(abs(lat) - 90) < MAX_ANGULAR_DIFF) || abs(lat) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GRI_nicol coordinate function, ", "1.0 / lat, lat = 0:", lat);

                if ((abs(lon) < MAX_ANGULAR_DIFF) || abs(abs(lon) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GRI_nicol coordinate function, ", "1.0 / lon, lon = 0:", lon);

                //Inverse equations
                final double Yr = Y - dy;

                final double b = PI / (2.0 * lon / RO) - 2.0 * lon / RO / PI;

                //Throw exception
                if (abs(b) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GRI_nicol coordinate function, ", "1.0 / b, b = 0:", b);

                final double cc = 2.0 * lat / RO / PI;
                final double dd = sin(lat / RO) - cc;

                //Throw exception
                if (abs(dd) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GRI_nicol coordinate function, ", "1.0 / dd, dd = 0:", dd);

                final double d = (1.0 - cc * cc) / dd;
                final double fy = 1.0 + d * d / (b * b);

                //Throw exception
                if (abs(fy) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GRI_nicol coordinate function, ", "1.0 / fy, fy = 0:", fy);

                final double g = sin(lat / RO);
                final double n = (d * d * g / (b * b) + d / 2.0) / fy;

                double q = n * n - (d * d * g * g / (b * b) + d * g - 1.0) / fy;

                //Throw exception
                if (q < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(q) in G_nicol coordinate function, ", "q < q: ", q);

                //Correct q
                if (q < 0.0) q = 0.0;

                //rlon = R * M_PI / 2.0 * (n - sign(lat) * sqrt(q)) - Y
                return R * PI / 2.0 * (n - signum(lat) * sqrt(q)) - Yr;
        }
        */
        
        public static double F_nicol(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if ((abs(lonr) < MAX_ANGULAR_DIFF) || ( abs(abs(lat) - 90) < MAX_ANGULAR_DIFF))
                {
                        X = dx;
                }

                else if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else if ( abs(abs(lonr) - 90) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO * cos(lat / RO) + dx;
                }

                else
                {
                        final double p = (1 - 4.0 * lonr * lonr / (PI * PI * RO * RO)) / (4.0 * lonr / PI / RO);
                        final double r = 2.0 * (sin(lat / RO) - 2.0 * lat / PI / RO);
                        
                        //Throw exception
                        if (abs(r) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_nicol coordinate function, ", "1.0 / r, r = 0:", r);

                        final double q = (1 - 4.0 * lat * lat / (PI * PI * RO * RO)) / r;
                        
                        //Solve quadratic equation
                        final double at = p * p + q * q;
                        final double bt = 2 * p * q * (q - sin(lat / RO));
                        final double ct = q * q * (sin(lat / RO) * sin(lat / RO) - 1.0);
                        final double D = bt * bt - 4.0 * at * ct;

                        final double x = lonr < 0 ? (-bt - sqrt(D)) / (2.0 * at) : (-bt + sqrt(D)) / (2.0 * at);

                        X = R * PI / 2.0 * x + dx;   
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_nicol coordinate function, ", "F_nicol > MAX_FLOAT: ", X);

                return X;
        }
        
        
        public static double G_nicol(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0; 

                if ((abs(lonr) < MAX_ANGULAR_DIFF) || ( abs(abs(lat) -90) < MAX_ANGULAR_DIFF))
                {
                        Y = R * lat / RO + dy;
                }

                else if ( abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else if ( abs(abs(lonr) - 90) < MAX_ANGULAR_DIFF)
                {
                        Y = R * PI / 2.0 * sin(lat / RO) + dy;
                }

                else
                {
                       	final double p = (1 - 4.0 * lonr * lonr / (PI * PI * RO * RO)) / (4.0 * lonr / PI / RO);
                        final double r = 2.0 * (sin(lat / RO) - 2.0 * lat / PI / RO);

                        //Throw exception
                        if (abs(r) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_nicol coordinate function, ", "1.0 / r, r = 0:", r);

                        final double q = (1 - 4.0 * lat * lat / (PI * PI * RO * RO)) / r;

                        //Compute X coordinate
                        final double X = F_nicol(lat, lon, R, lat1, lat2, lon0, dx, dy, c);

                        Y = R * PI / 2.0 * sin(lat / RO) - p / q * X  + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_nicol coordinate function, ", "G_nicol > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_nicol(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nicol coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_nicol coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the prime meridian
                if (abs(Xr) < MIN_FLOAT)
                        return Yr / R * RO;

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return 0.0;

                //Otherwise, find initial solution
                final double lat0 = 0.0;

                //Reduce coordinates
                final double x = 2.0 * Xr / (PI * R);
                final double y = 2.0 * Yr / (PI * R);
        
                //Find lat using Newton'smethod
                FILat fl = new FILat(x, y, R, lat1, lat2, lon0 , dx, dy, Projections::FILat_nicol);
                FILatDer fld = new FILatDer(x, y, R, lat1, lat2, lon0 , dx, dy, Projections::FILatDer_nicol);
                double lat = NewtonRaphson.findRoot(fl::function, fld::function, lat0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_nicol coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_nicol(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nicol coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_nicol coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the central meridian
                if (abs(Xr) < MIN_FLOAT)
                        return 0.0;

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return Xr / R * RO;

                //Otherwise, initial solution
                final double x = 2.0 * Xr / (PI * R);
                final double y = 2.0 * Yr / (PI * R);

                //Solve quadratic equation for lon
                final double at = 4.0 * x;
                final double bt = 2.0 * PI * (1.0 - x * x - y * y);
                final double ct = -PI * PI * x;
                final double D = bt * bt - 4.0 * at * ct;

                double lonr = (x > 0 ? (-bt + sqrt(D)) / (2.0 * at) : (-bt + sqrt(D)) / (2.0 * at)) * RO;

                //Throw exception
                if (lonr > MAX_LON + ARGUMENT_ROUND_ERROR || lonr < MIN_LON - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_nicol coordinate function, ", "abs(lonr)  > 90.", lonr);

                //Correct lonr
                if (lonr > MAX_LON) lonr = MAX_LON;
                else if (lonr < MIN_LON) lonr = MIN_LON;

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        public static double FILat_nicol(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                //F = (x^2 + y^2) * (pi * sin(latr) - 2 * latr) * pi + 4 * latr ^2 * (y - sin(latr)) + 2 * pi * latr - pi^2 * y
                final double F = ((X * X + Y * Y) * (PI * sin(lat_i / RO) - 2.0 * lat_i / RO) * PI + 4.0 * lat_i * lat_i / (RO * RO) * (Y - sin(lat_i / RO)) + 2.0 * PI * lat_i / RO - PI * PI * Y) * RO;

                return F;
        }


        public static double FILatDer_nicol(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                //dF = 2 * pi + 8 * lat * (y - sin(lat)) - 4 * lat^2 * cos(lat) + pi * (x^2 + y^2) * (pi * cos(lat) - 2)
                final double dF = 2 * PI + 8.0 * lat_i / RO * (Y - sin(lat_i / RO)) - 4.0 * lat_i * lat_i / (RO * RO) * cos(lat_i / RO) + PI * (X * X + Y * Y) * (PI * cos(lat_i / RO) - 2.0);

                return dF;
        }
   
        
        //Ortelius
        public static double F_ortel(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if (abs(lonr) < 90)
                {
                        //Call Apian projection
                        X = F_api(lat, lon, R, lat1, lat2, lon0, dx, dy, c);
                }

                else
                {
                        X = R * signum(lonr) * (sqrt(PI * PI / 4.0 - lat * lat / (RO * RO)) + abs(lonr / RO) - PI / 2) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_ortel coordinate function, ", "F_ortel > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_ortel(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_ortho coordinate function, ", "G_ortho > MAX_FLOAT: ", Y);

                return Y;
        }


        //Orthographic
        public static double F_ortho(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double rho = R * cos(lat / RO);
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_ortho coordinate function, ", "F_ortho > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_ortho(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double rho = R * cos(lat / RO);
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_ortho coordinate function, ", "G_ortho > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_ortho(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_ortho coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_ortho coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
               
                final double A = dist / (2 * R);
                
                //Throw exception
                if (abs(A) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(A) in FI_ortho coordinate function, ", "abs(A) > 1: ", A);

                final double lat = 90 - asin(A) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_ortho coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_ortho(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_ortho coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_ortho coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_ortho coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_ortho coordinate function, ", "abs(lonr) > 180.", lonr);
                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //Parabolic
        public static double F_parab(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = sqrt(3.0 / PI) * R * lonr / RO * (2.0 * cos(2.0 * lat / 3.0 / RO) - 1) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_parab coordinate function, ", "F_parab > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_parab(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = sqrt(3.0 * PI) * R * sin(lat / 3.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_parab coordinate function, ", "G_parab > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        
        //Peirce Quincuncial
        public static double F_peiq(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
	
               	final double a = cos(lat / RO) * ( cos(lonr / RO) - sin(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in F_peiq coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = cos(lat / RO) * ( sin(lonr / RO) + cos(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in F_peiq coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                final double  v = 0.5 * (A - B);
                double m = sqrt(2.0) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in F_peiq coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2.0) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in F_peiq coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double X =  R * 0.5 * (Ye - Xe) / sqrt(2.0) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_peiq coordinate function, ", "F_peiq > MAX_FLOAT: ", X);

                return X;
        }
    
        public static double G_peiq(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double a = cos(lat / RO) * (cos(lonr / RO) - sin(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in G_peiq coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = cos(lat / RO) * (sin(lonr / RO) + cos(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in G_peiq coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                final double  v = 0.5 * (A - B);
                double m = sqrt(2.0) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in G_peiq coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2.0) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in G_peiq coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double Y =  R * 0.5 * (Ye + Xe) / sqrt(2.0) + dy;
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_peiq coordinate function, ", "G_peiq > MAX_FLOAT: ", Y);

                return Y;
        }


        //Perspective Azimuthal
        public static double F_pers(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double h = sqrt(5.0);
                final double A = 1.0 - 1.0 / h * (1.0 - sin(lat / RO));
                final double rho = R * cos(lat / RO) / A;
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_pers coordinate function, ", "F_pers > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_pers(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double h = sqrt(5.0);
                final double A = 1.0 - 1.0 / h * (1.0 - sin(lat / RO));
                final double rho = R * cos(lat / RO) / A;
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_pers coordinate function, ", "G_pers > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_pers(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_pers coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_pers coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                //Solve: rho = R * cos(lat) / (1 - 1 / h * (1 - sin(lat)))
                //Leads to quadratic equation: rho^2 *[ (1 - 2 / h * (1 - sin(lat))) + 1 / h^2 * (1 - 2 * sin(lat) + sin^2(lat))] = (R*(1 - sin^2(lat)))^2
                //Substitutions: t = sin(lat)
                //		 A = rho^2 / h^2 + R^2,   
                //		 B = 2 * rho^2 / h - 2 * rho^2 / h^2
                //		 C = rho^2 - 2 * rho^2 / h + rho^2 / h^2 - R^2
                //D = 4 * R^2 * (R^2 - rho^2) + (8 * R^2 * rho^2) / h
                //t12 = (-B +- sqrt(D)) / (2 * A)
                //lat = asin(t)

                final double h = sqrt(5.0);
                final double A = dist * dist / (h * h) + R * R;
                final double B = (2 * dist * dist / h - 2 * dist * dist / (h * h));
                final double C = dist * dist - 2 * dist * dist / h + dist * dist / (h * h) - R * R;

                //Discriminant
                double D = B * B - 4 * A * C;

                double arg = 0.0;

                //D < 0
                if (D < -0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in FI_pers coordinate function, ", "D < 0: ", D);

                //D == 0
                else if (D == 0)
                        arg = -B / (2 * A);

                //D > 0
                else
                        arg = (-B + sqrt(D)) / (2 * A);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_pers coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_stere coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_pers(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_pers coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_pers coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate FI_stere coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Far-side Perspective Azimuthal
        public static double F_persf(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(c) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_persf coordinate function, ", "1.0 / c, c = 0:", c);	

                final double A = 1.0 - 1.0 / c * (1.0 - sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_persf coordinate function, ", "1.0 / A, A = 0:", A);

                final double rho = R * cos(lat / RO) / A;

                final double X = rho * sin(lonr / RO) + dx;


                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_persf coordinate function, ", "F_persf > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_persf(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(c) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_persf coordinate function, ", "1.0 / c, c = 0:", c);

                final double A = 1.0 - 1.0 / c * (1.0 - sin(lat / RO));
                final double rho = R * cos(lat / RO) / A;

                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_persf coordinate function, ", "G_persf > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FI_persf(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_persf coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluateFI_persf coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                
                //Solve: rho = R * cos(lat) / (1 - 1 / h * (1 - sin(lat)))
                //Leads to quadratic equation: rho^2 *[ (1 - 2 / h * (1 - sin(lat))) + 1 / h^2 * (1 - 2 * sin(lat) + sin^2(lat))] = (R*(1 - sin^2(lat)))^2
                //Substitutions: t = sin(lat)
                //		 A = rho^2 / h^2 + R^2,   
                //		 B = 2 * rho^2 / h - 2 * rho^2 / h^2
                //		 C = rho^2 - 2 * rho^2 / h + rho^2 / h^2 - R^2
                //D = 4 * R^2 * (R^2 - rho^2) + (8 * R^2 * rho^2) / h
                //t12 = (-B +- sqrt(D)) / (2 * A)
                //lat = asin(t)

                final double A = dist * dist / (c * c) + R * R;
                final double B = (2 * dist * dist / c - 2 * dist * dist / (c * c));
                final double C = dist * dist - 2 * dist * dist / c + dist * dist / (c * c) - R * R;

                //Discriminant
                double D = B * B - 4 * A * C;

                double arg = 0.0;

                //D < 0
                if (D < -0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in FI_persf coordinate function, ", "D < 0: ", D);

                //D == 0
                else if (D == 0)
                        arg = -B / (2 * A);

                //D > 0
                else
                        arg = (-B + sqrt(D)) / (2 * A);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_persf coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_persf coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_persf(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_persf coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_persf coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_persf coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Near-side Perspective Azimuthal
        public static double F_persn(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 1.0 + 1.0 / c * (1.0 + sin(lat / RO));
                final double rho = R * cos(lat / RO) / A;
                
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_persn coordinate function, ", "F_persn > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_persn(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 1.0 + 1.0 / c * (1.0 + sin(lat / RO));
                final double rho = R * cos(lat / RO) / A;
                
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_persn coordinate function, ", "G_persn > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_persn(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_persn coordinate function, ", "FI_persn > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_persn coordinate function, ", "GI_persn > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                
                //Solve: rho = R * cos(lat) / (1 + 1 / h * (1 + sin(lat)))
                //Leads to quadratic equation: rho^2 *[ (1 + 2 / h * (1 + sin(lat))) + 1 / h^2 * (1 + 2 * sin(lat) + sin^2(lat))] = (R*(1 - sin^2(lat)))^2
                //Substitutions: t = sin(lat)
                //		 A = rho^2 / h^2 + R^2,   
                //		 B = 2 * rho^2 / h + 2 * rho^2 / h^2
                //		 C = rho^2 + 2 * rho^2 / h + rho^2 / h^2 - R^2
                //D = 4 * R^2 * (R^2 - rho^2) - (8 * R^2 * rho^2) / h
                //t12 = (-B +- sqrt(D)) / (2 * A)
                //lat = asin(t)

                final double A = dist * dist / (c * c) + R * R;
                final double B = (2 * dist * dist / c + 2 * dist * dist / (c * c));
                final double C = dist * dist + 2 * dist * dist / c + dist * dist / (c * c) - R * R;

                //Discriminant
                double D = B * B - 4 * A * C;

                double arg = 0.0;

                //D < 0
                if (D < -0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in FI_persn coordinate function, ", "D < 0: ", D);

                //D == 0
                else if (D == 0)
                        arg = -B / (2 * A);

                //D > 0
                else
                        arg = (-B + sqrt(D)) / (2 * A);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_persn coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_persn coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_persn(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_persn coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_persn coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_persn coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Hassler Polyconic, American
        public static double F_poly(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                //If lat = 0
                if ( abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                //Otherwise
                else
                {
                        final double delta = lonr / RO * sin(lat / RO);

                        //Throw exception
                        if (abs(lat) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_poly coordinate function, ", "1.0 / lat, lat = 0:", lat);
                        
                        final double rho = R * 1.0 / tan(lat / RO);

                        X = rho * sin(delta) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_poly coordinate function, ", "F_poly > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_poly(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                //If lat = 0
                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = -R * lat1 / RO + dy;
                }

                //Otherwise
                else
                {
                        final double delta = lonr / RO * sin(lat / RO);

                        //Throw exception
        		if (abs(lat) < MIN_FLOAT)
                		throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_poly coordinate function, ", "1.0 / lat, lat = 0:", lat);

                        final double rho = R * 1.0 / tan(lat / RO);
                        final double S = R * lat / RO - R * lat1 / RO;

                        Y = S + rho * (1.0 - cos(delta)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_poly coordinate function, ", "G_poly > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FI_poly(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_poly coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_poly coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Find initial solution
                //F = X ^ 2 + R ^ 2 * (lat - lat0) ^ 2 + Y ^ 2 + 2 * R^2 * (lat - lat0)/tan(lat) - 2 * R * (lat - lat0) * Y - 2 * R / tan (lat) * Y 
                //G = F * sin^2(lat): Mofidify minimized function to avoid the singularity at lat = 0, F(0) = +- INF
                //G = (X ^ 2 + R ^ 2 * (lat - lat0) ^ 2 + Y ^ 2 - 2 * R * (lat - lat0) * Y) * (sin(lat)) ^ 2 + (R ^ 2 * (lat - lat0) - R * Y) * sin(2 * lat)
                //G = 0
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double lat_i = 0.0;
                final double dlat = lat_i - lat1;
                final double A = Xr * Xr + R * R * dlat * dlat / RO + Yr * Yr - 2 * R * dlat / RO * Yr;
                final double B = R * R * dlat / RO - R * Yr;
                final double C = sin(lat_i / RO);
                final double G = A * C * C + B * sin(2 * lat_i / RO);

                //First derivative
                //dG = (X ^ 2 + Y ^ 2 + R ^ 2 * (lat - lat0) ^ 2 + R ^ 2 - 2 * R * Y * (lat - lat0)) * sin(2 * lat) + (2 * R ^ 2 * (lat - lat0) - 2 * R * Y) * (cos(lat)) ^ 2
                final double d2 = Xr * Xr + Yr * Yr;
                final double D = d2 + R * R * dlat / RO * dlat / RO + R * R - 2 * R * Yr * dlat / RO;
                final double E = 2 * R * R * dlat / RO - 2 * R * Yr;
                final double F = cos(lat_i / RO);
                final double dG = D * sin(2 * lat_i / RO) + E * F * F;

                //Second derivative
                //DGG = 2 * cos(2 * lat) * (X ^ 2 + Y ^ 2 + R ^ 2 * (lat - lat0) ^ 2 + R ^ 2 - 2 * R * Y *(lat - lat0)) + (cos(lat)) ^ 2 * 2 * R ^ 2;
                final double DGG = 2 * cos(2 * lat_i / RO) * D + 2 * F * F * R * R;

                //Taylor's approximation
                //T(G(x)) = G(0) + dG(0)lat + 0.5 * dGG(0)*lat^2

                //Find lat from Taylor's approximation: solve quadratic equation
                final double ct = G;
                final double bt = dG;
                final double at = 0.5 * DGG;

                final double disc = bt * bt - 4 * at * ct;

                //Throw exception
                if (disc < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(disc) in FI_poly coordinate function, ", "disc < 0: ", disc);

                final double x1 = (-bt + sqrt(disc)) / (2 * at);
                final double x2 = (-bt - sqrt(disc)) / (2 * at);

                //Assign non-zero root
                final double lat0 = ((abs(x1) > abs(x2)) ? x1 : x2) * RO;

                //Find lat
                FILat fl = new FILat(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILat_poly);
                FILatDer fld = new FILatDer(X, Y, R, lat1, lat2, lon0 , dx, dy, Projections::FILatDer_poly);
                double lat = NewtonRaphson.findRoot(fl::function, fld::function, lat0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
          
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_poly coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }
        
        
        public static double GI_poly(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_poly coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_poly coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_poly(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                //If lat = 0
                double lonr = 0;
                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        lonr = Xr / R * RO;
                }
                
                //Otherwise
                else
                {
                        final double S = R * lat / RO - R * lat1 / RO;
                        final double rho = R / tan(lat / RO);
                        final double delta =  atan2(signum(lat) * Xr, signum(lat) * (S + rho - Yr)) * RO;
                        lonr =  delta / sin (lat / RO);
                }
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_poly coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        public static double FILat_poly(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double S = R * (lat_i - lat1) / RO;
                final double rho = R / tan(lat_i / RO);
                final double A = S + rho - Yr;
                final double F = (Xr * Xr + A * A - rho * rho) * RO;

                return F;
        }


        public static double FILatDer_poly(final double lat_i, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy)
        {
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double S = R * (lat_i - lat1) / RO;
                final double rho = R / tan(lat_i / RO);
                final double dF = 2 * rho * (R - S / tan(lat_i / RO) + Yr / tan(lat_i / RO));
	
                return dF;
        }
        
        
        //Putnins P1
        public static double F_putp1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 0.94745;
                final double B = 3.0;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in F_putp1 coordinate function, ", "D < 0: ", D);

                final double X = cx * R * lonr / RO * sqrt(D) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_putp1 coordinate function, ", "F_putp1 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 0.94745;

                final double Y = cy * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_putp1 coordinate function, ", "G_putp1 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_putp1(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp1 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp1 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 0.94745;

                final double lat = Yr / (R * cy) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp1(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp1 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp1 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_putp1(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 0.94745;
                final double B = 3.0;
                final double Xr = X - dx;
                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in GI_putp1 coordinate function, ", "D < 0: ", D);

                final double lonr = Xr / (R * cx * sqrt(D)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp1 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Putnins P2
        public static double F_putp2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double c1 = 1.89490;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);
                                
                //Find theta
                final double latr = lat / RO;
                final double theta0 = ( 0.615709 * latr + 0.00909953 * latr * latr * latr + 0.0046292 * latr * latr * latr * latr * latr) * RO;                
                FTheta ft = new FTheta(lat, Projections::FTheta_putp2);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_putp2);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
                
                final double X = c1 * R * lonr / RO * (cos(theta / RO) - 0.5) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_putp2 coordinate function, ", "F_putp2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double c2 = 1.71848;
                
                final double latr = lat / RO;
                
                //Find theta
                final double theta0 = (0.615709 * latr + 0.00909953 * latr * latr * latr + 0.0046292 * latr * latr * latr * latr * latr) * RO;
                FTheta ft = new FTheta(lat, Projections::FTheta_putp2);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_putp2);    
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);
               
                final double Y = c2 * R * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_putp2 coordinate function, ", "G_putp2 > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FTheta_putp2(final double lat, final double theta)
        {
                final double cp = (4.0 * PI - 3.0 * sqrt(3.0)) / 6.0;
                return 2.0 * theta / RO + sin(2.0 * theta / RO)  - 2.0 * sin(theta / RO) - cp * sin(lat / RO);
        }


        public static double FThetaDer_putp2(final double lat, final double theta)
        {
                return (2.0 + 2.0 * cos(2.0 * theta / RO) - 2.0 * cos(theta / RO)) / RO;
        }
        
        
        public static double FI_putp2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double c2 = 1.71848;

                double arg = Yr / (R * c2);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_putp2 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double cp = (4.0 * PI - 3.0 * sqrt(3)) / 6.0;
                double arg2 = (2.0 * theta / RO + sin(2 * theta / RO) - 2 * sin(theta / RO)) / cp;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_putp2 coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_putp2(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double c2 = 1.71848;
                final double Xr = X - dx;
                final double Yr = Y - dy;
                double arg = Yr / (R * c2);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_putp2 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double c1 = 1.89490;
                final double lonr = Xr / (c1 * R * (cos(theta / RO) - 0.5)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp2 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Putnins P3
        public static double F_putp3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = sqrt(2.0 / PI);
                final double A = 4.0;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = cx * R * lonr / RO * (1.0 - A * pow((lat / (RO * PI)), 2)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_putp3 coordinate function, ", "F_putp3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = sqrt(2.0 / PI);
                
                final double Y = cy * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_putp3 coordinate function, ", "G_putp3 > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_putp3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp3 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = sqrt(2.0 / PI);

                final double lat = Yr / (R * cy) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp3 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp3 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_putp3(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = sqrt(2.0 / PI);
                final double A = 4.0;
                final double Xr = X - dx;
               
                final double lonr = Xr / (R * cx * (1.0 - A * pow((lat / (RO * PI)), 2))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp3 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //Putnins P3P
        public static double F_putp3p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = sqrt(2.0 / PI);
                final double A = 2.0;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = cx * R * lonr / RO * (1.0 - A * pow((lat / (RO * PI)), 2)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_putp3p coordinate function, ", "F_putp3p > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp3p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = sqrt(2.0 / PI) * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_putp3p coordinate function, ", "G_putp3p > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_putp3p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp3p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp3p coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = sqrt(2.0 / PI);

                final double lat = Yr / (R * cy) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp3p coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp3p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp3p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp3p coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_putp3(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = sqrt(2.0 / PI);
                final double A = 2.0;
                final double Xr = X - dx;
               
                final double lonr = Xr / (R * cx * (1.0 - A * pow((lat / (RO * PI)), 2))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp3p coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Putnins P4P
        public static double F_putp4p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 2 * sqrt(0.6 / PI);
                final double A = 5.0 * sqrt(2.0) / 8.0;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                double arg = A * sin(lat / RO);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in F_putp4p coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg 
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;
                final double B = cos(theta / 3.0 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_putp4p coordinate function, ", "1.0 / B, B = 0:", B);

                final double X = cx * R * lonr / RO * cos(theta / RO) / B + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_putp4p coordinate function, ", "F_putp4p > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp4p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 2.0 * sqrt(1.2 * PI);
                final double A = 5.0 * sqrt(2.0) / 8.0;

                double arg = A * sin(lat / RO);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in G_putp4p coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg 
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double Y = cy * R * sin(theta / 3.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_putp4p coordinate function, ", "G_putp4p > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_putp4p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp4p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp4p coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 2.0 * sqrt(1.2 * PI);

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg  < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_putp4p coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = 3.0 * asin(arg) * RO;
                final double A = 5.0 * sqrt(2.0) / 8.0;

                double arg2 = sin(theta / RO) / A;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_putp4p coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp4p coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_putp4p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp4p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp4p coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double cx = 2 * sqrt(0.6 / PI);
                final double cy = 2.0 * sqrt(1.2 * PI);

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg  < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_putp4p coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = 3.0 * asin(arg) * RO;
                final double B = cos(theta / 3.0 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_putp4p coordinate function, ", "1.0 / B, B = 0:", B);

                final double lonr = Xr * B / (R * cx * cos(theta / RO)) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_putp4p coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Putnins P5
        public static double F_putp5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 1.01346;
                final double A = 2.0;
                final double B = 1.0;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = cx * R * lonr / RO * (A - sqrt(B + 12.0 * pow((lat / (RO * PI)), 2))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_putp5 coordinate function, ", "F_putp5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 1.01346;
                final double Y = cy * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_putp5 coordinate function, ", "G_putp5 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_putp5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp5 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 1.01346;

                final double lat = Yr / (R * cy) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp5 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp5 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_putp5(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 1.01346;
                final double A = 2.0;
                final double B = 1.0;
                final double Xr = X - dx;
               
                final double lonr = Xr / (R * cx * (A - sqrt(B + 12.0 * pow((lat / (RO * PI)), 2)))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp5 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Putnins P5P
        public static double F_putp5p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 1.01346;
                final double A = 1.5;
                final double B = 0.5;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = cx * R * lonr / RO * (A - sqrt(B + 12.0 * pow((lat / (RO * PI)), 2))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_putp5p coordinate function, ", "F_putp5p > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp5p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 1.01346;
                final double Y = cy * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_putp5p coordinate function, ", "G_putp5p > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_putp5p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp5p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp5p coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 1.01346;

                final double lat = Yr / (R * cy) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp5p coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp5p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp5p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp5p coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_putp5p(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 1.01346;
                final double A = 1.5;
                final double B = 0.5;
                final double Xr = X - dx;

                final double lonr = Xr / (R * cx * (A - sqrt(B + 12.0 * pow((lat / (RO * PI)), 2)))) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp5p coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Putnins P6
        public static double F_putp6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 1.01346;
                final double D = 2.0;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_putp6);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_putp6);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = cx * R * lonr / RO * (D - sqrt( 1.0 + theta / RO * theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_putp6 coordinate function, ", "F_putp6 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 0.9191;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_putp6);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_putp6);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy * R * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_putp6 coordinate function, ", "G_putp6 > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FTheta_putp6(final double lat, final double theta)
        {
                final double A = 4.0;
                final double B = 2.14714;
                final double r = sqrt(1.0 + theta / RO * theta / RO) ;

                return (A - r) * theta / RO - log(theta / RO + r) - B * sin(lat / RO);
      }

        
        public static double FThetaDer_putp6(final double lat, final double theta)
        {
                final double A = 4.0;
                return (A - 2.0 * sqrt(1.0 + theta / RO * theta / RO)) / RO;
        }
        
        public static double FI_putp6(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp6 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp6 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 0.9191;;

                final double theta = Yr / (R * cy) * RO;

                final double A = 4.0;
                final double B = 2.14714;
                final double r = sqrt(1.0 + theta / RO * theta / RO);

                double arg = ((A - r) * theta / RO - log(theta / RO + r)) / B;

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg6) in FI_putp6 coordinate function, ", "abs(arg6) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp6 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp6(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp6 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp6 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double cx = 1.01346;
                final double cy = 0.9191;;
                final double D = 2.0;
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double theta = Yr / (R * cy) * RO;
                final double lonr = Xr / (cx * R * (D - sqrt(1.0 + theta / RO * theta / RO))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp6 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //Putnins P6P
        public static double F_putp6p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 0.44329;
                final double D = 3.0;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_putp6p);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_putp6p);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = cx * R * lonr / RO * (D - sqrt(1.0 + theta / RO * theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_putp6p coordinate function, ", "F_putp6p > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_putp6p(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 0.80404;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double theta0 = lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_putp6p);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_putp6p);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy * R * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_putp6p coordinate function, ", "G_putp6p > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_putp6p(final double lat, final double theta)
        {
                final double A = 6.0;
                final double B = 5.61125;
                final double r = sqrt(1.0 + theta / RO * theta / RO);

                return (A - r) * theta / RO - log(theta / RO + r) - B * sin(lat / RO);
        }


        public static double FThetaDer_putp6p(final double lat, final double theta)
        {
                final double A = 6.0;
        	return (A - 2.0 * sqrt(1.0 + theta / RO * theta / RO)) / RO;
        }
        
        
        public static double FI_putp6p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp6p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_putp6p coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 0.80404;

                final double theta = Yr / (R * cy) * RO;

                final double A = 6.0;
                final double B = 5.61125;
                final double r = sqrt(1.0 + theta / RO * theta / RO);

                double arg = ((A - r) * theta / RO - log(theta / RO + r)) / B;

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_putp6p coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp6p coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_putp6p(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp6p coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_putp6p coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double cx = 0.44329;
                final double cy = 0.80404;
                final double D = 3.0;
                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double theta = Yr / (R * cy) * RO;
                final double lonr = Xr / (cx * R * (D - sqrt(1.0 + theta / RO * theta / RO))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp6p coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Quartic Authalic
        public static double F_qua_aut(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double q = 2.0;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                final double X = R * lonr / RO * cos(lat / RO) / cos(lat / q / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_qua_aut coordinate function, ", "F_qua_aut > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_qua_aut(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double p = 2.0;
                final double q = 2.0;
        
                final double Y = p * R * sin(lat / q / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_qua_aut coordinate function, ", "G_qua_aut > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_qua_aut(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_qua_aut coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_qua_aut coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double p = 2.0;
                final double q = 2.0;

                double arg =  Yr / (p * R);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in F_qua_aut coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double lat = q * asin(arg) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_qua_aut coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_qua_aut(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_qua_aut coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_qua_aut coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_qua_aut(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double q = 2.0;
                final double Xr = X - dx;

                final double lonr = Xr / (R * cos(lat / RO) / cos(lat / q / RO)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_qua_aut coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Rectangular Polyconic
        public static double F_rpoly(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0, A = 0;

                //If lat1 = 0
                if (abs(lat1) < MAX_ANGULAR_DIFF)
                {
                        A = 0.5 * lonr / RO;
                }

                //Otherwise
                else
                {
                        A = tan(0.5 * lonr / RO * sin(lat1 / RO)) / sin(lat1 / RO);
                }

                //If lat = 0
                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = 2.0 * R * A + dx;
                }

                //Otherwise
                else
                {
                        final double delta = 2.0 * atan(A * sin(lat / RO));

                        //Throw exception
                        if (abs(abs(lat) - MAX_LAT) < MAX_ANGULAR_DIFF)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate tan(lat) in F_rpoly coordinate function, ", "lat = +-90: ", lat);

                        final double rho = R / tan(lat / RO);
                        X = rho * sin(delta) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_rpoly coordinate function, ", "F_rpoly > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_rpoly(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0, A = 0;

                //If lat1 = 0
                if (abs(lat1) < MAX_ANGULAR_DIFF)
                {
                        A = 0.5 * lonr / RO;
                }

                //Otherwise
                else
                {
                        A = tan(0.5 * lonr / RO * sin(lat1 / RO)) / sin(lat1 / RO);
                }

                //If lat = 0
                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = -R * lat1 / RO + dy;
                }

                //Otherwise
                else
                {
                        final double delta = 2.0 * atan(A * sin(lat / RO));

                        //Throw exception
                        if (abs(abs(lat) - MAX_LAT) < MAX_ANGULAR_DIFF)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate tan(lat) in G_rpoly coordinate function, ", "lat = +-90: ", lat);

                        Y = R * (lat / RO - lat1 / RO + 1.0 / tan(lat / RO) * (1.0 - cos(E))) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_rpoly coordinate function, ", "G_rpoly > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_rpoly(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                 if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_rpoly coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_rpoly coordinate function, ", "Y > MAX_FLOAT: ", Y);

                 //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_rpoly coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);
                
               //Inverse equation: use the general polyconic projection
        	final double lat = FI_poly(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_rpoly coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }


        public static double GI_rpoly(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_rpoly coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_rpoly coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_rpoly coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);
                
                //Inverse equation
                final double lat = FI_poly(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double Yr = Y - dy;

                double A = 0;

                //If lat = 0
                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        A = Xr / (2.0 * R);
                }

                //Otherwise
                else
                {
                        final double S = R * lat / RO - R * lat1 / RO;
                        final double rho = R / tan(lat / RO);

                        final double delta = atan2(signum(lat) * Xr, signum(lat) * (S + rho - Yr)) * RO;
                        A = tan(0.5 * delta / RO) / sin(lat / RO);
                }

                double lonr = 0;
                //If lat1 = 0
                if (abs(lat1) < MAX_ANGULAR_DIFF)
                {
                        lonr = 2.0 * A * RO;
                }

                //Otherwise
                else
                {
                        final double C = sin(lat1 / RO);
                        lonr = 2.0 / C * atan(C * A) * RO;
                }      
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_rpoly coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Sinusoidal
        public static double F_sinu(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_sinu coordinate function, ", "F_sinu > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_sinu(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_sinu coordinate function, ", "G_psinu > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_sinu(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_sinu coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = (Y - dy) / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw  new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_sinu coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }


        public static double GI_sinu(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_sinu coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_sinu coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Throw exception
                if (abs(lat1) > MAX_LAT - MAX_ANGULAR_DIFF)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_sinu coordinate function, ", "1.0 / cos(lat1), lat1 = +-90.", lat1);
                
                //Get latitude
                final double lat = FI_sinu(X, Y, R, lat1, lat2, lon0, dx, dy, c);
                
                //Inverse equation
                final double lonr = (X - dx) / (R * cos(lat / RO)) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_sinu coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        

        //Solovyev Azimuthal
        public static double F_solo(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 4;

                //Throw exception
                if (abs(abs(A) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in F_solo coordinate function, ", "lat = +-90: ", lat);

                final double rho = 4.0 * R * tan(A / RO);
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_solo coordinate function, ", "F_solo > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_solo(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 4;

                //Throw exception
                if (abs(abs(A) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in G_solo coordinate function, ", "lat = +-90: ", lat);

                final double rho = 4.0 * R * tan(A / RO);
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_solo coordinate function, ", "G_solo > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_solo(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_solo coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_solo coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                final double lat = 90 - 4 * atan(dist / (4 * R)) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_solo coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }


        public static double GI_solo(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_solo coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_solo coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_solo coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Stereographic
        public static double F_stere(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2;

                //Throw exception
                if (abs(A - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in F_stere coordinate function, ", "lat = +-90: ", lat);
                
                final double rho = 2.0 * R * tan(A / RO);
                final double X = rho * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_stere coordinate function, ", "F_stere > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_stere(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2;

                //Throw exception
                if (abs(A - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in G_stere coordinate function, ", "lat = +-90: ", lat);

                final double rho = 2.0 * R * tan(A / RO);
                final double Y = -rho * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_stere coordinate function, ", "G_stere > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FI_stere(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_stere coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_stere coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double dist = sqrt(Xr * Xr + Yr * Yr);
                final double lat = 90 - 2 * atan(dist / (2 * R)) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_stere coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_stere(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_stere coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_stere coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double lonr = atan2(Xr, -Yr) * RO;
                
                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_stere coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Twilight General Vertical Perspective
        public static double F_twi(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.4;

                return F_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }


        public static double G_twi(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.4;

                return G_persf(lat, lon, R, lat1, lat2, lon0, dx, dy, h);
        }

        
         public static double FI_twi(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.4;

                return FI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }


        public static double GI_twi(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double h = 2.4;

                return GI_persf(X, Y, R, lat1, lat2, lon0, dx, dy, h);
        }
        

        //Urmaev V
        public static double F_urm5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double m = 2.0 * pow(3, 0.25) / 3.0;
                final double n = 0.8;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta = asin(n *sin(lat / RO)) * RO;

                final double X = m * R * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_urm5 coordinate function, ", "F_urm5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_urm5(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double m = 2.0 * pow(3, 0.25) / 3.0;
                final double n = 0.8;
                final double q = 0.414524;

                final double theta = asin(n * sin(lat / RO)) * RO;
                final double Y = R * theta / RO * (1.0 + q / 3.0 * theta * theta / (RO * RO)) / (m * n) + dy;
                
                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_urm5 coordinate function, ", "G_urm5 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_urm5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_urm5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_urm5 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double m = 2.0 * pow(3, 0.25) / 3.0;
                final double n = 0.8;
                final double q = 0.414524;

                final double Yr = Y - dy;

                //Solve: a * theta ^3 + b * theta + c, where a = q / (3 * m * n), b = 1 / (m * n), c = -Y/R
                final double at = q / (3 * m * n);
                final double bt = 1.0 / (m * n);
                final double ct = -Yr / R;

                final double f = (bt / at);
                final double g = (ct / at);
                double h = g * g / 4.0 + f * f * f / 27.0;

                //Throw exception
                if (h < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(h) in FI_urm5 coordinate function, ", "h < 0: ", h);

                //Correct h
                if (h < 0.0) h = 0.0;

                //Single real root + 2 imaginary(h > 0), we are interested in the real root only
                final double r = -g / 2.0 + sqrt(h);
                final double s = cbrt(r);
                double t = -g / 2.0 - sqrt(h);
                final double u = cbrt(t);
                final double theta = (s + u) * RO;
        
                double arg2 = 1.0 / n * sin(theta / RO);

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_urm5 coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_urm5 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_urm5(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_urm5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_urm5 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double m = 2.0 * pow(3, 0.25) / 3.0;
                final double n = 0.8;
                final double q = 0.414524;
        
                //Solve: a * theta ^3 + b * theta + c, where a = q / (3 * m * n), b = 1 / (m * n), c = -Y/R
                final double at = q / (3 * m * n);
                final double bt = 1.0 / (m * n);
                final double ct = -Yr / R;

                final double f = (bt / at);
                final double g = (ct / at);
                double h = g * g / 4.0 + f * f * f / 27.0;

                //Throw exception
                if (h < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(h) in GI_urm5 coordinate function, ", "h < 0: ", h);

                //Correct h
                if (h < 0.0) h = 0.0;

                //Single real root + 2 imaginary(h > 0), we are interested in the real root only
                final double r = -g / 2.0 + sqrt(h);
                final double s = cbrt(r);
                double t = -g / 2.0 - sqrt(h);
                final double u = cbrt(t);
                final double theta = (s + u) * RO;
                
                final double lonr = Xr / (m * R * cos(theta / RO)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_putp6p coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Van der Grinten I
        public static double F_vandg(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                final double B = abs(2.0 * lat /180);
                final double C = sqrt(1 - B * B);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF) || (abs(B - 1.0) < MAX_ANGULAR_DIFF))
                {
                        X = dx;
                }

                else
                {
                        //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);

                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = A * A;
                        final double E = B + C - 1;

                        //Throw exception
                        if (abs(E) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg coordinate function, ", "1 / E, E = 0:", E);

                        final double G = C / E;
                        
                        //Throw exception
                        if (abs(B) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg coordinate function, ", "1.0 / B, B = 0:", B);
                        
                        final double P = G * (2.0 / B - 1);
                        final double Q = D + G;
                        final double S = P * P + D;
                        final double TT = G - P * P;
                        double U = A * A * TT * TT - S * (G * G - P * P);

                        //Throw exception
                        if (U < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in F_vandg coordinate function, ", "U < 0: ", U);

                        //Correct U
                        if (U < 0.0) U = 0.0;

                        //Throw exception
                        if (abs(S) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg coordinate function, ", "1 / S, S = 0:", S);

                        X = R * PI * signum(lonr) * (A * TT + sqrt(U)) / S + dx;
                }


                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_vandg coordinate function, ", "F_vandg > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_vandg(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                final double B = abs(2.0 * lat / 180);
                final double C = sqrt(1 - B * B);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF))
                {
                        Y = R * signum(lat) * PI * B / (1.0 + C) + dy;
                }

                else if (abs(B - 1.0) < MAX_ANGULAR_DIFF)
                {
                        Y = R * signum(lat) * PI + dy;
                }

                else
                {
                        //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_vandg coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);
       
                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = A * A;
                        final double E = B + C - 1;

                        //Throw exception
                        if (abs(E) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_vandg coordinate function, ", "1 / E, E = 0:", E);

                        final double G = C / E;
                        
                        //Throw exception
                        if (abs(B) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_vandg coordinate function, ", "1.0 / B, B = 0:", B);
              
                        final double P = G * (2.0 / B - 1);
                        final double Q = D + G;
                        final double S = P * P + D;
                        double V = (A * A + 1) * S - Q * Q;

                        //Throw exception
                        if (V < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in G_vandg coordinate function, ", "V < 0: ", V);

                        //Correct V
                        if (V < 0.0) V = 0.0;
                        
                        //Throw exception
                        if (abs(S) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_vandg coordinate function, ", "1 / S, S = 0:", S);

                        Y = R * PI * signum(lat) * (P * Q - A * sqrt(V)) / S + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_vandg coordinate function, ", "G_vandg > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_vandg(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the prime meridian
                if (abs(Xr) < MIN_FLOAT)
                {
                        //Solve equation for t
                        final double t = 2 * PI * R * Yr / (PI * PI * R * R + Yr * Yr);

                        return t * PI / 2 * RO;
                }

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return 0.0;

                //Otherwise, find the inverse
                final double x = abs(Xr) / (R * PI);
                final double y = abs(Yr) / (R * PI);

                //Inverse: lat, solve the cubic equation: at^3 + bt^2 + ct + d = 0
                final double at = x * x * x * x + 2.0 * x * x * y * (1 + y) + (1 + y) * (1 + y) * (1 + y * y);
                final double bt = -2.0 * (x * x * (y - 1) + y * (1 + y) * (1 + y));
                final double ct = -4.0 * y * (1 + x * x + y * y);
                final double dt = 8.0 * y * y;

                //Get real roots of the quartic equation
                final double A = bt / at;
                final double B = ct / at;
                final double C = dt / at;

                //Coefficients of the Cardano's formula
                final double Q_ = (3.0 * B - A * A) / 9.0;
                final double R_ = (9.0 * A * B - 27.0 * C - 2.0 * A * A * A) / 54.0;

                //Solution for the 1. quadrant, add the sign
                final double theta = acos(R_ / sqrt(-Q_ * Q_ * Q_));
                final double t = 2.0 * sqrt(-Q_) * cos((theta + 4.0 * PI) / 3.0) - A / 3.0;

                final double lat = signum(Y) * PI / 2.0 * t * RO ;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_vandg coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_vandg(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_vandg coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_vandg coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the central meridian
                if (abs(Xr) < MIN_FLOAT)
                        return 0;

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return Xr / R * RO;

                //Otherwise, find the inverse
                final double x = abs(Xr) / (R * PI);;
                final double y = abs(Yr) / (R * PI);;

                //Solve the quadratic equation: as^2 + bs + c = 0
                final double at = x;
                final double bt = 1.0 - x * x - y * y;
                final double ct = -x;

                final double D = bt * bt - 4.0 * at * ct;

                //Find s: second root for the first quadrant
                final double s = (-bt + sqrt(D)) / (2.0 * at);

                //Solution for the 1. quadrant, add the sign
                final double lonr = signum(Xr) * s * PI * RO;
                
                 //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_vandg coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Van der Grinten II
        public static double F_vandg2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                final double B = abs(2.0 * lat /180);
                final double C = sqrt(1 - B * B);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF) || (abs(B - 1.0) < MAX_ANGULAR_DIFF))
                {
                        X = dx;
                }

                else
                {
                        //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg2 coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);

                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = 1 + A * A * B * B;

                        //Throw exception
                        if (abs(D) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg2 coordinate function, ", "1 / D, D = 0:", D);

                        final double X1 = (C * sqrt(1 + A * A) - A * C * C) / D;

                        X = R * PI * signum(lonr) * X1 + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_vandg2 coordinate function, ", "F_vandg2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_vandg2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                final double B = abs(2.0 * lat / 180);
                final double C = sqrt(1 - B * B);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF))
                {
                        Y = R * signum(lat) * PI * B / (1.0 + C) + dy;
                }

                else if (abs(B - 1.0) < MAX_ANGULAR_DIFF)
                {
                        Y = R * signum(lat) * PI + dy;
                }

                else
                {
                        //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_vandg2 coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);

                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = 1 + A * A * B * B;

                        //Throw exception
                        if (abs(D) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate G_vandg2 coordinate function, ", "1 / D, D = 0:", D);

                        final double X1 = (C * sqrt(1 + A * A) - A * C * C) / D;
                        double E = 1 - X1 * X1 - 2.0 * A * X1;

                        //Throw exception
                        if (E < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(E) in G_vandg2 coordinate function, ", "E < 0: ", E);
                                              
                        //Correct E
                        if (E < 0.0) E = 0.0;  
                        
                        Y = R * PI * signum(lat) * sqrt(E) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_vandg2 coordinate function, ", "G_vandg2 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_vandg2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the prime meridian
                if (abs(Xr) < MIN_FLOAT)
                {
                        //Solve equation for t
                        final double t = 2 * PI * R * Yr / (PI * PI * R * R + Yr * Yr);

                        return t * PI / 2 * RO;
                }

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return 0.0;

                //Otherwise, find the inverse
                final double x = Xr / (R * PI);
                final double y = Yr / (R * PI);

                //Find t
                final double t = 2.0 * y / (1 + x * x + y * y);

                final double lat = PI / 2.0 * t * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_vandg2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_vandg2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
               	//Analogous inverse equation to van der Grinten I.
                return GI_vandg(X, Y, R, lat1, lat2, lon0, dx, dy, c);
        }
        
        
        //Van der Grinten III
        public static double F_vandg3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                final double B = abs(2.0 * lat /180);
                final double C = sqrt(1 - B * B);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF) || (abs(B - 1.0) < MAX_ANGULAR_DIFF))
                {
                        X = dx;
                }

                else
                {
                        //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg3 coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);

                        final double A = 0.5 * abs(180.0 / lonr - lonr / 180.0);
                        final double Y1 = B / (1 + C);
                        double D = 1.0 + A * A - Y1 * Y1;

                        //Throw exception
                        if (D < -ARGUMENT_ROUND_ERROR)
                                 throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(D) in F_vandg3 coordinate function, ", "D < 0: ", D);

                        //Correct D
                        if (D < 0.0) D = 0.0;

                        X = R * PI * signum(lonr) * (sqrt(D) - A) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_vandg3 coordinate function, ", "F_vandg3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_vandg3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                final double B = abs(2.0 * lat / 180);
                final double C = sqrt(1.0 - B * B);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF))
                {
                        Y = R * signum(lat) * PI * B / (1.0 + C) + dy;
                }

                else if (abs(B - 1.0) < MAX_ANGULAR_DIFF)
                {
                        Y = R * signum(lat) * PI + dy;
                }

                else
                {
                       final double Y1 = B / (1 + C);
                       
                       Y =  R * PI * signum(lat) * Y1 + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_vandg3 coordinate function, ", "G_vandg3 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_vandg3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the prime meridian
                if (abs(Xr) < MIN_FLOAT)
                {
                        //Solve equation for t
                        final double t = 2 * PI * R * Yr / (PI * PI * R * R + Yr * Yr);

                        return t * PI / 2 * RO;
                }

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return 0.0;

                //Otherwise, find the inverse
                final double x = Xr / (R * PI);
                final double y = Yr / (R * PI);

                //Find t
                final double t = 2.0 * y / (1 + y * y);

                final double lat = PI / 2.0 * t * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_vandg2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_vandg3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
               	//Analogous inverse equation to van der Grinten I.
                return GI_vandg(X, Y, R, lat1, lat2, lon0, dx, dy, c);
        }
        
        
        //Van der Grinten IV
        public static double F_vandg4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                final double B = abs(2.0 * lat / 180);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF) || (abs(B - 1.0) < MAX_ANGULAR_DIFF))
                {
                        X = dx;
                }

                else
                {
                        final double B1 = B * B * (B - 1);

                        //Throw exception
                        if (abs(B1) < MIN_FLOAT)
                                throw  new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_vandg4 coordinate function, ", "1.0 / B1, B1 = 0:", B1);

                        final double C = 0.5 * (B * (8.0 - B * (2.0 + B * B)) - 5) / B1;
                        
                         //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw  new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_vandg4 coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);
                        
                        final double C1 = 90 / lonr + lonr / 90;
                        double C2 = C1 * C1 - 4;

                        //Throw exception
                        if (C2 < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in F_vandg4 coordinate function, ", "C2 < 0: ", C2);

                        //Correct C2
                        if (C2 < 0.0) C2 = 0.0;
                        
                        final double D = signum(abs(lonr) - 90) * sqrt(C2);
                        final double F1 = (B + C) * (B + C);
                        final double F2 = (B + 3.0 * C) * (B + 3.0 * C);
                        double F = F1 * (B * B + C * C * D * D - 1) + (1 - B * B) * (B * B * (F2 + 4.0 * C * C) + 12 * B * C * C * C + 4.0 * C * C * C * C);

                        //Throw exception
                        if (F < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in F_vandg4 coordinate function, ", "F < 0: ", F);

                        //Correct F
                        if (F < 0.0) F = 0.0;

                        final double G = 4.0 * F1 + D * D;

                        //Throw exception
                        if (abs(G) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg4 coordinate function, ", "1 / G, G = 0:", G);

                        final double X1 = (D * (F1 + C * C - 1) + 2.0 * sqrt(F)) / G;

                        X = R * PI * 0.5 * signum(lonr) * X1 + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_vandg4 coordinate function, ", "F_vandg4 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_vandg4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                final double B = abs(2.0 * lat / 180);

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else if ((abs(lonr) < MAX_ANGULAR_DIFF) || (abs(B - 1.0) < MAX_ANGULAR_DIFF))
                {
                        Y = R * lat / RO + dy;
                }

                else
                {
                        final double B1 = B * B * (B - 1);

                        //Throw exception
                        if (abs(B1) < MIN_FLOAT)
                                throw  new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_vandg4 coordinate function, ", "1.0 / B1, B1 = 0:", B1);

                        final double C = 0.5 * (B * (8.0 - B * (2.0 + B * B)) - 5) / B1;
                        
                        //Throw exception
                        if (abs(lonr) < MIN_FLOAT)
                                throw  new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_vandg4 coordinate function, ", "1.0 / lonr, lonr = 0:", lonr);
 
                        final double C1 = 90 / lonr + lonr / 90;
                        double C2 = C1 * C1 - 4;

                        //Throw exception
                        if (C2 < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in F_vandg4 coordinate function, ", "C2 < 0: ", C2);

                        //Correct C2
                        if (C2 < 0.0) C2 = 0.0;

                        final double D = signum(abs(lonr) - 90) * sqrt(C2);
                        final double F1 = (B + C) * (B + C);
                        final double F2 = (B + 3.0 * C) * (B + 3.0 * C);
                        double F = F1 * (B * B + C * C * D * D - 1) + (1 - B * B) * (B * B * (F2 + 4.0 * C * C) + 12 * B * C * C * C + 4.0 * C * C * C * C);

                        //Throw exception
                        if (F < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in G_vandg4 coordinate function, ", "F < 0: ", F);

                        //Correct C2
                        if (F < 0.0) F = 0.0;

                        final double G = 4.0 * F1 + D * D;

                        //Throw exception
                        if (abs(G) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate F_vandg4 coordinate function, ", "1 / G, G = 0:", G);

                        final double X1 = (D * (F1 + C * C - 1) + 2.0 * sqrt(F)) / G;
                        double H = 1 + D * abs(X1) - X1 * X1;

                        //Throw exception
                        if (H < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(H) in G_vandg4 coordinate function, ", "H < 0: ", H);
                        
                        //Correct H
                        if (H < 0.0) H = 0.0;

                        Y = R * PI * 0.5 * signum(lat) * sqrt(H) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_vandg4 coordinate function, ", "G_vandg4 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_vandg4(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg4 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_vandg4 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the prime meridian
                if (abs(Xr) < MIN_FLOAT)
                {
                        return Yr / R * RO;
                }

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return 0.0;

                //Otherwise, find the inverse
                final double x = abs(Xr) / (0.5 * R * PI);
                final double y = abs(Yr) / (0.5 * R * PI);

                //Inverse: lat, solve the cubic equation: at^3 + bt^2 + ct + d = 0
                final double at = 1.0 + y;
                final double bt = -(x * x - 3.0 + y + y * y);
                final double ct = -(5.0 + 3.0 * y);
                final double dt = 5.0 * y;

                //Get real roots of the quartic equation
                final double A = bt / at;
                final double B = ct / at;
                final double C = dt / at;

                //Coefficients of the Cardano's formula
                final double Q_ = (3.0 * B - A * A) / 9.0;
                final double R_ = (9.0 * A * B - 27.0 * C - 2.0 * A * A * A) / 54.0;

                //Solution for the 1. quadrant, add the sign
                final double theta = acos(R_ / sqrt(-Q_ * Q_ * Q_));
                final double t = 2.0 * sqrt(-Q_) * cos((theta + 4.0 * PI) / 3.0) - A / 3.0;

                final double lat = signum(Y) * PI / 2.0 * t * RO ;
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_vandg4 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_vandg4(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_vandg4 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_vandg4 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Point on the central meridian
                if (abs(Xr) < MIN_FLOAT)
                        return 0;

                //Point on the equator
                if (abs(Yr) < MIN_FLOAT)
                        return Xr / R * RO;

                //Otherwise, find the inverse
                final double x = abs(Xr) / (0.5 * R * PI);
                final double y = abs(Yr) / (0.5 * R * PI);

                //Solve the quadratic equation: as^2 + bs + c = 0
                final double at = x;
                final double bt = 1.0 - x * x - y * y;
                final double ct = -x;

                final double D = bt * bt - 4.0 * at * ct;

                //Find s: second root for the first quadrant
                final double s = (-bt + sqrt(D)) / (2.0 * at);

                //Solution for the 1. quadrant, add the sign
                final double lonr = signum(Xr) * s * PI / 2.0 * RO;
                
                 //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_vandg4 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Wagner I
        public static double F_wag1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double M = 2.0 * sqrt(sqrt(3)) / 3.0;
                final double N = 0.5 * sqrt(3);
                final double A = N * sin(lat / RO);

                if (abs(A) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(A) in F_wag1 coordinate function, ", "abs(A) > 1: ", A);

                final double theta = asin(A) * RO;

                final double X = R * M * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate F_wag1 coordinate function, ", "F_wag1 > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_wag1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double M = 2.0 * sqrt(sqrt(3)) / 3.0;
                final double N = 0.5 * sqrt(3);
                final double A = N * sin(lat / RO);

                if (abs(A) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(A) in G_wag1 coordinate function, ", "abs(A) > 1: ", A);

                final double theta = asin(A) * RO;

                final double Y = R * 3.0 * theta / RO * M * N /2.0 + dy;		//Error in Evenden G, I: Cartographic Projection Procedures, page 7

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate G_wag1 coordinate function, ", "G_wag1 > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Wagner II
        public static double F_wag2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double c1 = 0.88022;
                final double c2 = 0.88550;
                final double c3 = 0.92483;
                final double theta = asin(c1 * sin(c2 * lat / RO)) * RO;
                final double X = c3 * R * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wag2 coordinate function, ", "F_wag2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wag2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double c1 = 0.88022;
                final double c2 = 0.88550;
                final double c4 = 1.38725;
                final double theta = asin(c1 * sin(c2 * lat / RO)) * RO;
                final double Y = c4 * R * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wag2 coordinate function, ", "G_wag2 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_wag2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag2 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                final double Yr = Y - dy;
                final double lat = Yr / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_wag2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_wag2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag2 coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_wag2(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double c1 = 0.88022;
                final double c2 = 0.88550;
                final double c3 = 0.92483;

                double arg = c1 * sin(c2 * lat / RO);

                // Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in GI_wag2 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double lonr = Xr  / (R * c3 * cos (theta / RO)) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eck5 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Wagner III
        public static double F_wag3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = lat1 / RO;
                final double B = cos(2.0 * A / 3.0);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate F_wag3 coordinate function, ", "1 / A, A = 0:", A);


                final double X = R * lonr / RO * (cos(A) / B) * cos(2.0 * lat / 3.0 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wag3 coordinate function, ", "F_wag3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wag3(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wag3 coordinate function, ", "G_wag3 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_wag3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag3 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                final double Yr = Y - dy;
                final double lat = Yr / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_wag3 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_wag3(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag3 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag3 coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double A = lat1 / RO;
                final double B = cos(2.0 * A / 3.0);

                final double lat = FI_wag3(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double lonr = Xr / (R * cos(A) / B * cos (2.0 * lat / 3.0 / RO)) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eck5 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Wagner IV
        public static double F_wag4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double r = sqrt(2.0 * PI * sin(PI / 3.0) / (2.0 * PI / 3.0 + sin(2.0 * PI / 3.0)));
                final double c1 = 2.0 * r / PI;
        
                final double lonr = CartTransformation.redLon0(lon, lon0);
           
                final double theta0 = 0.5 * lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_wag4);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_wag4);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = c1 * R * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wag4 coordinate function, ", "F_wag4 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wag4(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double r = sqrt(2.0 * PI * sin(PI / 3.0) / (2.0 * PI / 3.0 + sin(2.0 * PI / 3.0)));
                final double c2 = 2.0 * r / sqrt(3.0);
                
                final double theta0 = 0.5 * lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_wag4);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_wag4);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = c2 * R * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wag4 coordinate function, ", "G_wag4 > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_wag4(final double lat, final double theta)
        {
                final double cp = 2.0 / 3.0  * PI + sqrt(3.0) / 2.0;
                return 2.0 * theta / RO + sin(2.0 * theta / RO) - cp * sin(lat / RO);
        }


        public static double FThetaDer_wag4(final double lat, final double theta)
        {
                return ( 2.0 + 2.0 * cos(2.0 * theta / RO)) / RO;
        }
        
        
        public static double FI_wag4(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag4 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag4 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                //Inverse equation
                final double Yr = Y - dy;
                final double r = sqrt(2.0 * PI * sin(PI / 3.0) / (2.0 * PI / 3.0 + sin(2.0 * PI / 3.0)));
                final double c2 = r * 2.0 / sqrt(3.0);
                
                double arg = Yr / (R * c2);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_wag4 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double cp = 2.0 / 3.0  * PI + sqrt(3.0 / 2.0);
                final double theta = asin(arg) * RO;
                double arg2 = (2 * theta / RO + sin(2 * theta / RO)) / cp;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_wag4 coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                final double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_wag4 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_wag4(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_moll coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_moll coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_wag4(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double r = sqrt(2.0 * PI * sin(PI / 3.0) / (2.0 * PI / 3.0 + sin(2.0 * PI / 3.0)));
                final double c2 = r * 2.0 / sqrt(3.0);
                final double Xr = X - dx;
                final double Yr = Y - dy;
                double arg = Yr / (R * c2);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_wag4 coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;
                
                final double c1 = 2.0 * r / PI;
                final double lonr = Xr / (c1 * R * cos(theta / RO)) * RO;

                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_moll coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Wagner VI
        public static double F_wag6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 1.89490;
                final double A = -0.5;
                final double B = 3.0;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in F_wag6 coordinate function, ", "D < 0: ", D);

                final double X = cx * R * lonr / RO * (A + sqrt(D)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wag6 coordinate function, ", "F_wag6 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wag6(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = 0.94745;
                
                final double Y = cy * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wag6 coordinate function, ", "G_wag6 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_wag6(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag6 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag6 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = 0.94745;

                final double lat = Yr / (R * cy) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_putp2 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_wag6(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag6 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag6 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double lat = FI_wag6(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double cx = 1.89490;
                final double A = -0.5;
                final double B = 3.0;
                final double Xr = X - dx;
                final double D = 1.0 - B * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in GI_wag6 coordinate function, ", "D < 0: ", D);

                final double lonr = Xr / (R * cx * (A + sqrt(D))) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_wag6 coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Wagner VII
        public static double F_wag7(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double cx = 2.66723;
                final double ct = sin(65.0/RO);

                final double sin_th = ct * sin(lat / RO);
                final double sin2_th = sin_th * sin_th;

                //Throw exception
                if (sin2_th > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(sin2_th) in F_wag7 coordinate function, ", "sin2_th < 0: ", sin2_th);

                final double cos_th = sqrt(1.0 - sin2_th);
                final double cos2_al = 0.5 * (1.0 + cos_th * cos(lonr / 3.0 / RO));

                //Throw exception
                if (abs(cos2_al) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_wag7 coordinate function, ", "1.0 / cos2_al, cos2_al = 0:", cos2_al);

                final double cos_al = sqrt(cos2_al);
                final double X = cx * R * cos_th / cos_al * sin(lonr / 3.0 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wag7 coordinate function, ", "F_wag7 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wag7(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double cy = 1.24104;
                final double ct = sin(65.0 / RO);

                final double sin_th = ct * sin(lat / RO);
                final double sin2_th = sin_th * sin_th;

                //Throw exception
                if (sin2_th > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(sin2_th) in G_wag7 coordinate function, ", "sin2_th < 0: ", sin2_th);

                final double cos_th = sqrt(1.0 - sin2_th);
                final double cos2_al = 0.5 * (1.0 + cos_th * cos(lonr / 3.0 / RO));

                //Throw exception
                if (abs(cos2_al) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate G_wag7 coordinate function, ", "1.0 / cos2_al, cos2_al = 0:", cos2_al);

                final double cos_al = sqrt(cos2_al);
                final double Y = cy * R * sin_th / cos_al + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wag7 coordinate function, ", "G_wag7 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_wag7(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag7 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wag7 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double cx = 2.66723;
                final double cy = 1.24104;
                final double ct = sin(65.0 / RO);

                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double A = 4.0 * R * R * cx * cx * cy * cy * Yr * Yr - cx * cx * Yr * Yr * Yr * Yr - Xr * Xr * Yr * Yr * cy * cy;

                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in F_wag7 coordinate function, ", "A < 0: ", A);

                final double sin_th = sqrt(A) / (2.0 * R * R * cx * cy * cy);
                double sin_lat = sin_th / ct;

                //Throw exception
                if (sin_lat > 1.0 + ARGUMENT_ROUND_ERROR || sin_lat < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(sin_lat) in FI_wag7 coordinate function, ", "abs(sin_lat) > 1: ", sin_lat);

                //Correct sin_lat
                if (sin_lat > 1.0) sin_lat = 1.0;
                else if (sin_lat < -1.0) sin_lat = -1.0;

                final double lat = signum(Yr) * asin(sin_lat) * RO;
          
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_larr coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }
        
        
        public static double GI_wag7(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag7 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wag7 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double cx = 2.66723;
                final double cy = 1.24104;
                final double ct = sin(65.0 / RO);

                final double Xr = X - dx;
                final double Yr = Y - dy;

                final double A = 4.0 * R * R * cx * cx * cy * cy * Yr * Yr - cx * cx * Yr * Yr * Yr * Yr - Xr * Xr * Yr * Yr * cy * cy;

                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in F_wag7 coordinate function, ", "A < 0: ", A);

                double sin_th = sqrt(A) / (2.0 * R * R * cx * cy * cy);

                //Throw exception
                if (sin_th > 1.0 + ARGUMENT_ROUND_ERROR || sin_th < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(sin_th) in GI_wag7 coordinate function, ", "abs(sin_th) > 1: ", sin_th);

                //Correct sin_th
                if (sin_th > 1.0) sin_th = 1.0;
                else if (sin_th < -1.0) sin_th = -1.0;

                final double theta = signum(Yr) * asin(sin_th);
                final double lonr = atan2(Xr * Yr *cy *sin(theta), cx * (2 * R * R * cy * cy * sin(theta) * sin(theta) - Yr * Yr)) * 3.0 * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_larr coordinate function, ", "abs(lonr)  > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Werner-Staab
        public static double F_wer(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if (abs (90 - lat) < MAX_ANGULAR_DIFF)
                {
                        X = dx;
                }

                else
                {
                        X = R * (90 - lat) / RO * sin(lonr * cos(lat / RO) / (90 - lat)) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wer coordinate function, ", "F_wer > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wer(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                if (abs(90 - lat) < MAX_ANGULAR_DIFF)
                {
                        Y = dy;
                }

                else
                {
                        Y = R * (lat - 90) / RO * cos(lonr * cos(lat / RO) / (90 - lat)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wer coordinate function, ", "G_wer > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FI_wer(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wer coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_weren coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                final double rho = sqrt(Xr * Xr + Yr * Yr);
                final double lat = 90.0 - rho / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_wer coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_wer(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wer coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wer coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                
                final double rho = sqrt(Xr * Xr + Yr * Yr);
                final double lat = 90.0 - rho / R * RO;
                final double lonr = (90.0 - lat) / cos(lat / RO) * atan2(Xr, -Yr);

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_wer coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }
        
        
        //Werenskiold I
        public static double F_weren(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = 5.0 * sqrt(2.0) / 8.0;

                final double lonr = CartTransformation.redLon0(lon, lon0);

                double arg = A * sin(lat / RO);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in F_weren coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg 
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;
                final double B = cos(theta / 3.0 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate F_weren coordinate function, ", "1.0 / B, B = 0:", B);

                final double X = R * lonr / RO * cos(theta / RO) / B + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_weren coordinate function, ", "F_weren > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_weren(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = PI * sqrt(2.0);
                final double A = 5.0 * sqrt(2.0) / 8.0;

                double arg = A * sin(lat / RO);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in G_weren coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg 
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = asin(arg) * RO;

                final double Y = cy * R * sin(theta / 3.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_weren coordinate function, ", "G_weren > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_weren(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_weren coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_weren coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = PI * sqrt(2.0);

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg  < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_weren coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = 3.0 * asin(arg) * RO;
                final double A = 5.0 * sqrt(2.0) / 8.0;

                double arg2 = sin(theta / RO) / A;

                //Throw exception
                if (arg2 > 1.0 + ARGUMENT_ROUND_ERROR || arg2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg2) in FI_weren coordinate function, ", "abs(arg2) > 1: ", arg2);

                //Correct arg2
                if (arg2 > 1.0) arg2 = 1.0;
                else if (arg2 < -1.0) arg2 = -1.0;

                double lat = asin(arg2) * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_weren coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_weren(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_weren coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_weren coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double cy = PI * sqrt(2.0);

                double arg = Yr / (R * cy);

                //Throw exception
                if (arg > 1.0 + ARGUMENT_ROUND_ERROR || arg  < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(arg) in FI_weren coordinate function, ", "abs(arg) > 1: ", arg);

                //Correct arg
                if (arg > 1.0) arg = 1.0;
                else if (arg < -1.0) arg = -1.0;

                final double theta = 3.0 * asin(arg) * RO;
                final double B = cos(theta / 3.0 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_weren coordinate function, ", "1.0 / B, B = 0:", B);

                final double lonr = Xr * B / (R * cos(theta / RO)) * RO;

                //Throw exception
                if (abs(lonr) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_weren coordinate function, ", "abs(lonr) > 180.", lonr);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }

        
        //Wiechel
        public static double F_wiech(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double X = R * ( sin (lonr / RO) * cos (lat / RO) - cos (lonr / RO) * (1 - sin (lat / RO))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wiech coordinate function, ", "F_wiech > MAX_FLOAT: ", X);

                return X;
        }
        

        public static double G_wiech(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double Y = -R * (cos(lonr / RO) * cos(lat / RO) + sin(lonr / RO) * (1 - sin(lat / RO))) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wiech coordinate function, ", "G_wiech > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Winkel I
        public static double F_wink1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 0.5 * R * lonr * (cos(lat1 / RO) + cos(lat / RO)) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wink1 coordinate function, ", "F_wink1 > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double G_wink1(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wink1 coordinate function, ", "G_wink1 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_wink1(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wink1 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wink1 coordinate function, ", "Y > MAX_FLOAT: ", Y);
                
                final double Yr = Y - dy;
                final double lat = Yr / R * RO;

                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_wink1 coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;                
        }

         
        public static double GI_wink1(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck5 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_eck5 coordinate function, ", "Y > MAX_FLOAT: ", Y);
   
                //Inverse equation
                final double lat = FI_wink1(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double lonr = 2 * Xr / (R * (cos(lat1 / RO) + cos(lat / RO))) * RO;
        
                //Throw exception
        	if (abs(lonr) > MAX_LON)
                	throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_eck5 coordinate function, ", "abs(lonr)  > 180.", lonr);
	                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                return lon;
        }


        //Winkel II
        public static double F_wink2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cx = 0.5;
                
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = 0.9 * lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_wink2);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_wink2);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double E = cos(lat1 / RO) + cos(theta / RO);
                final double X = cx * R * lonr / RO * E + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wink2 coordinate function, ", "F_wink2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wink2(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                final double cy = PI / 4.0;
                final double D = 2.0 / PI;
                
                final double theta0 = 0.9 * lat;
                FTheta ft = new FTheta(lat, Projections::FTheta_wink2);
                FThetaDer ftd = new FThetaDer(lat, Projections::FThetaDer_wink2);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = cy * R * (sin(theta / RO) + D * lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wink2 coordinate function, ", "G_wink2 > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_wink2(final double lat, final double theta)
        {
                return 2.0 * theta / RO + sin(2.0 * theta / RO) - PI * sin(lat / RO);
        }


        public static double FThetaDer_wink2(final double lat, final double theta)
        {
                return (2.0 + 2.0 * cos(2.0 * theta / RO)) / RO;
        }
        
        
        public static double FI_wink2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wink2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wink2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Yr = Y - dy;
                final double cy = PI / 4.0;
                final double D = 2.0 / PI;
                final double arg = Yr / (cy * R);
                
                final double theta0 = - FITheta_wink2(arg, 0.0) / FIThetaDer_wink2(arg, 0.0);
                
                //Throw exception
                if (abs(theta0) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_wink2 coordinate function, ", "abs(theta0)  > 90.", theta0);
                
                FTheta ft = new FTheta(arg, Projections::FITheta_wink2);
                FThetaDer ftd = new FThetaDer(arg, Projections::FIThetaDer_wink2);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double lat = (arg * RO - sin(theta / RO) * RO) / D;
                
                if (Double.isNaN(lat)) {
                        System.out.println("Error");
                }
                
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_boggs coordinate function, ", "abs(lat)  > 90.", lat);

                return lat;
        }

         
        public static double GI_wink2(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wink2 coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wink2 coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                final double Xr = X - dx;
                final double Yr = Y - dy;
                final double cx = 0.5;
                final double cy = PI / 4.0;
                final double D = 2.0 / PI;

                final double arg = Yr / (cy * R);
                
                final double theta0 = -FITheta_wink2(arg, 0.0) / FIThetaDer_wink2(arg, 0.0);
                
                //Throw exception
                if (abs(theta0) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_wink2 coordinate function, ", "abs(theta0)  > 90.", theta0);

                FTheta ft = new FTheta(arg, Projections::FITheta_wink2);
                FThetaDer ftd = new FThetaDer(arg, Projections::FIThetaDer_wink2);
                final double theta = NewtonRaphson.findRoot(ft::function, ftd::function, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double lat = (arg * RO - sin(theta / RO) * RO) / D;
    
                //Throw exception
                if (abs(lat) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_wink2 coordinate function, ", "abs(lat)  > 90.", lat);

                final double E = cos(lat1 / RO) + cos(theta / RO);

                //Throw exception
                if (abs(E) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate GI_wink2 coordinate function, ", "1.0 / E, E = 0:", E);

                final double lonr = Xr / (R * cx * E) * RO;
                
                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr, -lon0);

                //Throw exception
                if (abs(lon) > MAX_LON)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate GI_wink2 coordinate function, ", "abs(lon)  > 180.", lon);
                
                return lon;
        }
        

        public static double FITheta_wink2(final double arg, final double theta)
        {
                // f(theta, Y, R, cy) = D * asin(0.5 * D * (2.0 * theta / RO + sin(2.0 * theta / RO))) + sin(theta / RO) - arg
                
                //Throw exception
                if (abs(theta) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FITheta_wink2 coordinate function, ", "abs(theta0)  > 90.", theta);
               
                final double D = 2.0 / PI;

                return D * asin(0.5 * D * (2.0 * theta / RO + sin(2.0 * theta / RO))) + sin(theta / RO) - arg;
        }


        public static double FIThetaDer_wink2(final double lat, final double theta)
        {
                // df / dtheta = (cos(theta) + D^2 * (1 + cos(2.0 * theta)) / sqrt(1 - (0.5* D * (2 * theta + sin(2 * theta))) ^ 2));
                
                //Throw exception
                if (abs(theta) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FIThetaDer_wink2 coordinate function, ", "abs(theta0)  > 90.", theta);

                final double D = 2.0 / PI;

                final double A = 0.5 * D * (2.0 * theta / RO + sin(2.0 * theta / RO));
                double B = 1.0 - A * A;

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate FI_wink2 coordinate function, ", "1 / B, B = 0:", B);
                
                //Correct B
                if (B < 0.0) B = 0.0;
                
                return (D * D * (1.0 + cos(2.0 * theta / RO)) / sqrt(B) + cos(theta / RO)) / RO;
        }

        
        //Winkel Tripel
        public static double F_wintri(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Aitoff projection
                final double X1 = F_aitoff(lat, lon, R, lat1, lat2, lon0, dx, dy, c);

                //Equidistant cylindrical
                final double X2 = F_eqc(lat, lon, R, lat1, lat2, lon0, dx, dy, c);

                //Average of both projections
                final double X = 0.5 * (X1 + X2);

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate F_wintri coordinate function, ", "F_wintri > MAX_FLOAT: ", X);

                return X;
        }


        public static double G_wintri(final double lat, final double lon, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Aitoff projection
                final double Y1 = G_aitoff(lat, lon, R, lat1, lat2, lon0, dx, dy, c);

                //Equidistant cylindrical
                final double Y2 = G_eqc(lat, lon, R, lat1, lat2, lon0, dx, dy, c);

                //Average of both projections
                final double Y = 0.5 * (Y1 + Y2);

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate G_wintri coordinate function, ", "G_wintri > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        public static double FI_wintri(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wintri coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate FI_wintri coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                //Initial solution
                double [] lat = {FI_aitoff(X, Y, R, lat1, lat2, lon0, dx, dy, c)};
                double [] lonr = {GI_aitoff(X, Y, R, lat1, lat2, lon0, dx, dy, c)};
                
                //Find solution using Gauss-Newton method
                final double fx = GN(Projections::JI_wintri, Projections::FRI_wintri, Projections::GRI_wintri, X, Y, R, lat1, lat2, lon0, dx, dy, c, 1.0e-4, lat, lonr);                  
                
                //double [] lat = {0}, lonr = {0};
                //FIGI_wintri(X, Y, R, lat1, lat2, lon0, dx, dy, c, 0.0001, lat, lonr);

                //Throw exception
                if (abs(lat[0]) > MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate FI_wintri coordinate function, ", "abs(lat)  > 90.", lat[0]);

                return lat[0];
        }

         
        public static double GI_wintri(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wintri coordinate function, ", "X > MAX_FLOAT: ", X);

                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate GI_wintri coordinate function, ", "Y > MAX_FLOAT: ", Y);

                //Inverse equation
                //Initial solution
                double [] lat = {FI_aitoff(X, Y, R, lat1, lat2, lon0, dx, dy, c)};
                double [] lonr = {GI_aitoff(X, Y, R, lat1, lat2, lon0, dx, dy, c)};
                
                //Find solution using Gauss-Newton method
                final double fx = GN(Projections::JI_wintri, Projections::FRI_wintri, Projections::GRI_wintri, X, Y, R, lat1, lat2, lon0, dx, dy, c, 1.0e-4, lat, lonr);                  
                
                //double [] lat = {0}, lonr = {0};
                //FIGI_wintri(X, Y, R, lat1, lat2, lon0, dx, dy, c, 0.0001, lat, lonr);

                //Throw exception
                if (abs(lonr[0]) > MAX_LON)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate GI_wintri coordinate function, ", "abs(lonr) > 180.", lonr[0]);

                //Reduce longitude
                final double lon = CartTransformation.redLon0(lonr[0], -lon0);

                return lon;
        }

        
        public static void JI_wintri(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c, double [] aj, double [] bj, double [] cj, double [] dj)
        {
                //Jacobian matrix J(2,2) = ( aj bj; cj dj) elements
                final double Yr = Y - dy;

                //F = rlat = (2 * x - R * lon * cos(lat0)) - 2 / tan(lat) * sin(lon/2) * (2 * y - R * lat);
                //G = rlon = (2 * y - R * lat) - R * acos(cos(lat) * cos(lon/2)) * sin(lat)/sqrt(1 - (cos(lat)*cos(lon/2))^2);

                //Compute values of the Jacobian matrix J = (a b ; c d)
                // a = j(1, 1) = diff(F, lat)
                // b = j(1, 2) = diff(F, lon)
                aj[0] = (2.0 * sin(0.5 * lon / RO) * (2.0 * Yr - R * lat / RO + (R * sin(2.0 * lat / RO)) / 2.0)) / (sin(lat / RO) * sin(lat / RO));
                bj[0] = -R * cos(lat1 / RO) - cos(0.5 * lon / RO) / tan(lat / RO) * (2.0 * Yr - R * lat / RO);

                // c = j(2, 1) = diff(G, lat)
                // d = j(2, 2) = diff(G, lon)
                final double p1 = cos(lat / RO) * cos(0.5 * lon / RO);
                final double p2 = acos(p1);
                final double p3 = 1 - p1 * p1;
                final double p4 = cos(0.5 * lon / RO) * sin(lat / RO) * sin(lat / RO);

                cj[0] = -R - R * p2 * cos(lat / RO) / sqrt(p3) - R * p4 / p3 + R * p4 * p1 * p2 / pow(p3, 1.5);
                dj[0] = -R * sin(0.5 * lon / RO) * sin(2.0 * lat / RO) / (4.0 * p3) +
                        R * sin(lon / RO) * p2 * cos(lat / RO) * cos(lat / RO) * sin(lat / RO) / (4.0 * pow(p3, 1.5));
        }

        
        public static double FRI_wintri(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Compute lat residual
                final double Xr = X - dx;
                final double Yr = Y - dy;

                //rlat = ((2 * x - R * lon * cos(lat0)) / (2 * y - R * lat)) ^ 2 - 4 * R ^ 2 / (tan(lat)) ^ 2 * (sin(lon / 2)) ^ 2
                return  (2.0 * Xr - R * lon / RO * cos(lat1 / RO)) - 2.0 / tan(lat / RO) * sin(lon / 2.0 / RO) * (2.0 * Yr - R * lat / RO);
        }

        public static double GRI_wintri(final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c)
        {
                //Compute lon residual
                final double Yr = Y - dy;
                final double p1 = cos(lat / RO) * cos(0.5 * lon / RO);
                final double p2 = acos(p1);
                final double p3 = 1 - p1 * p1;

                //rlon = (2 * y - R * lon) ^ 2 - R ^ 2 * (acos(cos(lat) * cos(lon / 2))) ^ 2 * (sin(lat)) ^ 2 / (1 - (cos(lat)*cos(lon / 2)) ^ 2);
                return (2.0 * Yr - R * lat / RO) - R * p2 * sin(lat / RO) / sqrt(p3);
        }
        
        /*
        public static void FIGI_wintri(final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c, final double eps, double [] lat, double [] lon)
        {
                //Initial lat / lon determined from Aitoff projection
                lat[0] = FI_aitoff(X, Y, R, lat1, lat2, lon0, dx, dy, c);
                lon[0] = GI_aitoff(X, Y, R, lat1, lat2, lon0, dx, dy, c);

                final double Xr = X - dx;
                final double Yr = Y - dy;

                //Apply Gauss-Newton method, J(2,2) 
                final int MAX_ITER = 20;
                int iterations = 0;

                //Minimized function
                //2x = 2 * R * theta * cos(lat) * sin(0.5 * lon) / sin(theta) + R * lon * cos(lat0)
                //2y = R * theta * sin(lat) / sin(theta) + R * lat
                //cos(theta) = cos(lat) * cos(lon/2)
                //
                //F = rlat = (2 * x - R * lon * cos(lat0)) - 2 / tan(lat) * sin(lon/2) * (2 * y - R * lat);
                //G = rlon = (2 * y - R * lat) - R * acos(cos(lat) * cos(lon/2)) * sin(lat)/sqrt(1 - (cos(lat)*cos(lon/2))^2);
                //phi = min(rlat * rlat + rlon * rlon)
                //Solved using the non/linear least squares: Gauss-Newton method

                while (iterations < MAX_ITER)
                {
                        //Compute values of the Jacobian matrix J = (a b ; c d)
                        // a = j(1, 1) = diff(F, lat)
                        // b = j(1, 2) = diff(F, lon)
                        final double aj = (2.0 * sin(0.5 * lon[0] / RO) * (2.0 * Yr - R * lat[0] / RO + (R * sin(2.0 * lat[0] / RO)) / 2.0)) / (sin(lat[0] / RO) * sin(lat[0] / RO));
                        final double bj = -R * cos(lat1 / RO) - cos(0.5 * lon[0] / RO) / tan(lat[0] / RO) * (2.0 * Yr - R * lat[0] / RO);

                        // c = j(2, 1) = diff(G, lat)
                        // d = j(2, 2) = diff(G, lon)
                        final double p1 = cos(lat[0] / RO) * cos(0.5 * lon[0] / RO);
                        final double p2 = acos(p1);
                        final double p3 = 1 - p1 * p1;
                        final double p4 = cos(0.5 * lon[0] / RO) * sin(lat[0] / RO) * sin(lat[0] / RO);

                        final double cj = -R - R * p2 * cos(lat[0] / RO) / sqrt(p3) - R * p4 / p3 + R * p4 * p1 * p2 / pow(p3, 1.5);
                        final double dj = -R * sin(0.5 * lon[0] / RO) * sin(2.0 * lat[0] / RO) / (4.0 * p3) + 
                                           R * sin(lon[0] / RO) * p2 * cos(lat[0] / RO) * cos(lat[0] / RO) * sin(lat[0] / RO) / (4.0 * pow(p3, 1.5));

                        // trans(J) * J
                        final double A = aj * aj + cj * cj;
                        final double B = aj * bj + cj * dj;
                        final double C = B;
                        final double D = bj * bj + dj * dj;

                        //Determinant trans(J) * J
                        final double deter = A * D - B * C;

                        //Inverse matrix: inv(trans(J) * J)
                        final double AI = D / deter;
                        final double BI = -B / deter;
                        final double CI = -C / deter;
                        final double DI = A / deter;

                        //Compute residuals
                        //rlat = ((2 * x - R * lon * cos(lat0)) / (2 * y - R * lat)) ^ 2 - 4 * R ^ 2 / (tan(lat)) ^ 2 * (sin(lon / 2)) ^ 2
                        //rlon = (2 * y - R * lon) ^ 2 - R ^ 2 * (acos(cos(lat) * cos(lon / 2))) ^ 2 * (sin(lat)) ^ 2 / (1 - (cos(lat)*cos(lon / 2)) ^ 2);
                        final double rlat = (2.0 * Xr - R * lon[0] / RO * cos(lat1 / RO)) - 2.0 / tan(lat[0] / RO) * sin(lon[0] / 2.0 / RO) * (2.0 * Yr - R * lat[0] / RO);
                        final double rlon = (2.0 * Yr - R * lat[0] / RO) - R * p2 * sin(lat[0] / RO) / sqrt(p3);

                        //Compute trans(J) * r
                        final double glat = aj * rlat + cj * rlon;
                        final double glon = bj * rlat + dj * rlon;

                        //Compute inv(trans(J) * J) * trans(J) * R
                        final double dlat = -(AI * glat + BI * glon);
                        final double dlon = -(CI * glat + DI * glon);

                        //Assign solution
                        lat[0] = lat[0] + dlat * RO;
                        lon[0] = lon[0] + dlon * RO;

                        //Stop iteration
                        if ((abs(dlat) < eps) && (abs(dlon) < eps))
                                break;

                        iterations++;
                }
        }
        */
        
        //Other functions
        public static double GN(ICoordFunctionProjJI function_ji, ICoordFunctionProjRI function_fri, ICoordFunctionProjRI function_gri, final double X, final double Y, final double R, final double lat1, final double lat2, 
                                final double lon0, final double dx, final double dy, final double c, final double eps, double [] lat, double [] lon)
        {
                //Gauss-Newton method for the Jacobian matrix J(2,2)
                //Analytic solution of the inverse, line-search technique not used
                //Used for solving the system of non-linear equations f(lat, lon)=0, g(lat, lon) = 0 representing the projection inverse
                final int MAX_ITER = 5;
                int iterations = 0;
                double fx = 0.0;
                
                while (iterations < MAX_ITER)
                {
                        //Compute values of the Jacobian matrix J = (a b ; c d)
                        double [] aj = {0}, bj = {0}, cj = {0}, dj = {0};
                        function_ji.f(lat[0], lon[0], X, Y, R, lat1, lat2, lon0, dx, dy, c, aj, bj, cj, dj);

                        //Compute trans(J) * J
                        final double A = aj[0] * aj[0] + cj[0] * cj[0];
                        final double B = aj[0] * bj[0] + cj[0] * dj[0];
                        final double C = B;
                        final double D = bj[0] * bj[0] + dj[0] * dj[0];

                        //Determinant trans(J) * J
                        final double deter = A * D - B * C;

                        //Inverse matrix: inv(trans(J) * J)
                        final double AI = D / deter;
                        final double BI = -B / deter;
                        final double CI = -C / deter;
                        final double DI = A / deter;

                        //Compute residuals
                        final double rlat = function_fri.f(lat[0], lon[0], X, Y, R, lat1, lat2, lon0, dx, dy, c);
                        final double rlon = function_gri.f(lat[0], lon[0], X, Y, R, lat1, lat2, lon0, dx, dy, c);
                        fx = rlat * rlat + rlon * rlon;
                        
                        //Compute trans(J) * r
                        final double glat = aj[0] * rlat + cj[0] * rlon;
                        final double glon = bj[0] * rlat + dj[0] * rlon;

                        //Compute inv(trans(J) * J) * trans(J) * R
                        final double dlat = -(AI * glat + BI * glon);
                        final double dlon = -(CI * glat + DI * glon);

                        //Assign solution
                        lat[0] = lat[0] + dlat * RO;
                        lon[0] = lon[0] + dlon * RO;

                        //Stop iteration
                        if ((abs(dlat) < eps) && (abs(dlon) < eps))
                                break;

                        iterations++;
                }
                
                return fx;
        }
}