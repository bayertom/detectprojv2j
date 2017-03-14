// Description: List of all implemented map projections
// Supported equations in the non-closed form,

// Copyright (c) 2015 - 2016
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
import static detectprojv2j.types.TTransformedLongitudeDirection.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.exceptions.*;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.numintegration.NumIntegration;
import detectprojv2j.forms.MainApplication;


public class Projections {
        
        public static void init (final List <Projection> projections, final TTransformedLongitudeDirection default_lon_dir)
        {
                //Initialize all supported projections and add them to the list
                ProjectionMiscellaneous adamh = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_adamh, Projections::Y_adamh, "adamh");
                ProjectionMiscellaneous adams1 = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_adams1, Projections::Y_adams1, "adams1");
                ProjectionMiscellaneous adams2 = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_adams2, Projections::Y_adams2, "adams2");
                ProjectionConic aea = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_aea, Projections::Y_aea, "aea");
                ProjectionAzimuthal aeqd = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_aeqd, Projections::Y_aeqd, "aeqd");
                ProjectionPseudoAzimuthal aitoff = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_aitoff, Projections::Y_aitoff, "aitoff");
                ProjectionMiscellaneous api = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_api, Projections::Y_api, "api");
                ProjectionMiscellaneous apiel = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_apiel, Projections::Y_apiel, "apiel");
                ProjectionMiscellaneous armad = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_armad, Projections::Y_armad, "armad");
                ProjectionMiscellaneous august = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_august, Projections::Y_august, "august");
                
                ProjectionMiscellaneous bacon = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_bacon, Projections::Y_bacon, "bacon");
                ProjectionPseudoCylindrical boggs = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_boggs, Projections::Y_boggs, "boggs");
                ProjectionPseudoConic bonne = new ProjectionPseudoConic (R0, 90.0, 0.0, 40.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_bonne, Projections::Y_bonne, "bonne");
                ProjectionAzimuthal breus = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_breus, Projections::Y_breus, "breus");
                ProjectionCylindrical cc = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_cc, Projections::Y_cc, "cc");
                ProjectionCylindrical cea = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_cea, Projections::Y_cea, "cea");
                ProjectionAzimuthal clar = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_clar, Projections::Y_clar, "breus");
                ProjectionMiscellaneous collg = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_collg, Projections::Y_collg, "collg");
                ProjectionMiscellaneous crast = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_crast, Projections::Y_crast, "crast");
                ProjectionMiscellaneous cwe = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_cwe, Projections::Y_cwe, "cwe");
                
                ProjectionPseudoCylindrical denoy = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_denoy, Projections::Y_denoy, "denoy");
                ProjectionPseudoCylindrical eck1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eck1, Projections::Y_eck1, "eck1");
                ProjectionPseudoCylindrical eck2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eck2, Projections::Y_eck2, "eck2");
                ProjectionPseudoCylindrical eck3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eck3, Projections::Y_eck3, "eck3");
                ProjectionPseudoCylindrical eck4 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eck4, Projections::Y_eck4, "eck4");
                ProjectionPseudoCylindrical eck5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eck5, Projections::Y_eck5, "eck5");
                ProjectionPseudoCylindrical eck6 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eck6, Projections::Y_eck6, "eck6");
                ProjectionMiscellaneous eisen = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eisen, Projections::Y_eisen, "eisen");
                ProjectionCylindrical eqc = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eqc, Projections::Y_eqc, "eqc");
                ProjectionConic eqdc = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eqdc, Projections::Y_eqdc, "eqdc");
                
                ProjectionConic eqdc2 = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eqdc2, Projections::Y_eqdc2, "eqdc2");
                ProjectionConic eqdc3 = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_eqdc3, Projections::Y_eqdc3, "eqdc3");
                ProjectionPseudoCylindrical fahey = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_fahey, Projections::Y_fahey, "fahey");
                ProjectionPseudoCylindrical fouc = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_fouc, Projections::Y_fouc, "fouc");
                ProjectionPseudoCylindrical fouc_s = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_fouc_s, Projections::Y_fouc_s, "fouc_s");
                ProjectionMiscellaneous fourn = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_fourn, Projections::Y_fourn, "fourn");
                ProjectionMiscellaneous fourn2 = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_fourn2, Projections::Y_fourn2, "fourn2");
                ProjectionCylindrical gall = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_gall, Projections::Y_gall, "gall");
                ProjectionPseudoCylindrical gins8 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_gins8, Projections::Y_gins8, "gins8");
                ProjectionAzimuthal gnom = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_gnom, Projections::Y_gnom, "gnom");
                
                ProjectionPseudoCylindrical goode = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_goode, Projections::Y_goode, "goode");
                ProjectionMiscellaneous guyou = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_guyou, Projections::Y_guyou, "guyou");
                ProjectionPseudoAzimuthal hammer = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_hammer, Projections::Y_hammer, "hammer");
                ProjectionPseudoCylindrical hataea = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_hataea, Projections::Y_hataea, "hataea");
                ProjectionAzimuthal hire = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_hire, Projections::Y_hire, "hire");                
                ProjectionAzimuthal jam = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_jam, Projections::Y_jam, "jam");
                ProjectionPseudoCylindrical kav5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_kav5, Projections::Y_kav5, "kav5");
                ProjectionPseudoCylindrical kav7 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_kav7, Projections::Y_kav7, "kav7");
                ProjectionAzimuthal laea = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_laea, Projections::Y_laea, "laea");
                ProjectionMiscellaneous larr = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_larr, Projections::Y_larr , "larr");
                
                ProjectionMiscellaneous lagrng = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_lagrng, Projections::Y_lagrng, "lagrng");
                ProjectionPseudoCylindrical lask = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_lask, Projections::Y_lask, "lask");
                ProjectionConic lcc = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_lcc, Projections::Y_lcc, "lcc");
                ProjectionConic leac = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_leac, Projections::Y_leac, "leac");
                ProjectionConic leac2 = new ProjectionConic (R0, 90.0, 0.0, 40.0, 50.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_leac2, Projections::Y_leac2, "leac2");
                ProjectionMiscellaneous litt = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_litt, Projections::Y_litt, "litt");
                ProjectionPseudoCylindrical loxim = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_loxim, Projections::Y_loxim, "loxim");
                ProjectionPseudoCylindrical mbt_s = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_mbt_s, Projections::Y_mbt_s, "mbt_s");
                ProjectionPseudoCylindrical mbt_s3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_mbt_s3, Projections::Y_mbt_s3, "mbt_s3");
                ProjectionPseudoCylindrical mbtfpq = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_mbtfpq, Projections::Y_mbtfpq, "mbtfpq");
                
                ProjectionPseudoCylindrical mbtfps = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_mbtfps, Projections::Y_mbtfps, "mbtfps");
                ProjectionCylindrical merc = new ProjectionCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_merc, Projections::Y_merc, "merc");
                ProjectionPseudoCylindrical mill = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_mill, Projections::Y_mill, "mill"); 
                ProjectionPseudoCylindrical moll = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_moll, Projections::Y_moll, "moll");
                ProjectionPseudoCylindrical nell = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_nell, Projections::Y_nell, "nell");
                ProjectionPseudoCylindrical nell_h = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_nell_h, Projections::Y_nell_h, "nell_h");    
                ProjectionMiscellaneous nicol = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_nicol, Projections::Y_nicol, "nicol");
                ProjectionPseudoCylindrical ortel = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_ortel, Projections::Y_ortel, "ortel");
                ProjectionAzimuthal ortho = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_ortho, Projections::Y_ortho, "ortho");
                ProjectionPseudoCylindrical parab = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_parab, Projections::Y_parab, "parab");
                
                ProjectionMiscellaneous peiq = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_peiq, Projections::Y_peiq, "peiq");
                ProjectionAzimuthal pers = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_pers, Projections::Y_pers, "pers");
                ProjectionAzimuthal persf = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_persf, Projections::Y_persf, "persf");
                ProjectionAzimuthal persn = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_persn, Projections::Y_persn, "persn");
                ProjectionPolyConic poly = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_poly, Projections::Y_poly, "poly");
                ProjectionPseudoCylindrical putp1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp1, Projections::Y_putp1, "putp1");
                ProjectionPseudoCylindrical putp2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp2, Projections::Y_putp2, "putp2");
                ProjectionPseudoCylindrical putp3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp3, Projections::Y_putp3, "putp3");
                ProjectionPseudoCylindrical putp3p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp3p, Projections::Y_putp3p, "putp3p");
                ProjectionPseudoCylindrical putp4p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp4p, Projections::Y_putp4p, "putp4p");
                
                ProjectionPseudoCylindrical putp5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp5, Projections::Y_putp5, "putp5");
                ProjectionPseudoCylindrical putp5p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp5p, Projections::Y_putp5p, "putp5p");         
                ProjectionPseudoCylindrical putp6 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp6, Projections::Y_putp6, "putp6");
                ProjectionPseudoCylindrical putp6p = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_putp6p, Projections::Y_putp6p, "putp6p");         
                ProjectionPseudoCylindrical qua_aut = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_qua_aut, Projections::Y_qua_aut, "qua_aut");
                ProjectionPseudoCylindrical rpoly = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_rpoly, Projections::Y_rpoly, "rpoly");
                ProjectionPseudoCylindrical sinu = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_sinu, Projections::Y_sinu, "sinu");     
                ProjectionAzimuthal solo = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_solo, Projections::Y_solo, "solo");
                ProjectionAzimuthal stere = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_stere, Projections::Y_stere, "stere");
                ProjectionAzimuthal twi = new ProjectionAzimuthal (R0, 90.0, 0.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_twi, Projections::Y_twi, "twi");
                
                ProjectionPseudoCylindrical urm5 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_urm5, Projections::Y_urm5, "urm5");
                ProjectionPolyConic vandg = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_vandg, Projections::Y_vandg, "vandg");
                ProjectionPolyConic vandg2 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_vandg2, Projections::Y_vandg2, "vandg2");
                ProjectionPolyConic vandg3 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_vandg3, Projections::Y_vandg3, "vandg3");
                ProjectionPolyConic vandg4 = new ProjectionPolyConic (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_vandg4, Projections::Y_vandg4, "vandg4");
                ProjectionPseudoCylindrical wag1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wag1, Projections::Y_wag1, "wag1");
                ProjectionPseudoCylindrical wag2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wag2, Projections::Y_wag2, "wag2");                
                ProjectionPseudoCylindrical wag3 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wag3, Projections::Y_wag3, "wag3");
                ProjectionPseudoCylindrical wag4 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wag4, Projections::Y_wag4, "wag4");
                ProjectionPseudoCylindrical wag6 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wag6, Projections::Y_wag6, "wag6");
                
                ProjectionPseudoCylindrical wag7 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wag7, Projections::Y_wag7, "wag7");
                ProjectionPseudoAzimuthal wer = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wer, Projections::Y_wer, "wer");
                ProjectionPseudoCylindrical weren = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_weren, Projections::Y_weren, "weren");
                ProjectionPseudoCylindrical wink1 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wink1, Projections::Y_wink1, "wink1");
                ProjectionPseudoCylindrical wink2 = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wink2, Projections::Y_wink2, "wink2");
                ProjectionPseudoAzimuthal wintri = new ProjectionPseudoAzimuthal (R0, 90.0, 0.0, 10.0, default_lon_dir, 0.0, 0.0, 0.0, 1.0, Projections::X_wintri, Projections::Y_wintri, "wintri");

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
                projections.add(boggs);
                projections.add(bonne);
                projections.add(breus);
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
                projections.add(mbt_s);
                projections.add(mbt_s3);
                projections.add(mbtfpq);
                
                projections.add(mbtfps);
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
                projections.add(wink1);
                projections.add(wink2);
                projections.add(wintri);
        
        }
        
        public static double X_def(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                return lat / RO;
        }

        public static double  Y_def(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                return lon / RO;
        }
        
        
        //Adams Hemisphere in a square
        public static double X_adamh(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double a = cos(lat / RO) * sin(lonr / RO);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in X_adamh coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double B = 0.5 * PI - lat / RO;
                final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in X_adamh coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in X_adamh coordinate function, ", "abs(n) > 1: ", n);

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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_adamh coordinate function, ", "X_adamh > MAX_FLOAT: ", X);

                return X;
        }

        public static double Y_adamh(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double a = cos(lat / RO) * sin(lonr / RO);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in Y_adamh coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double B = 0.5 * PI - lat / RO;
                final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in Y_adamh coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in Y_adamh coordinate function, ", "abs(n) > 1: ", n);

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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_adamh coordinate function, ", "Y_adamh > MAX_FLOAT: ", Y);

                return Y;
        }


        //Adams World in a Square I
        public static double X_adams1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = (cos(phi) * sin(0.5 * lonr / RO) - sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in X_adams1 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = (cos(phi) * sin(0.5 * lonr / RO) + sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in X_adams1 coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in X_adams1 coordinate function, ", "abs(n) > 1: ", n);

                //Correct n
                if (n > 1.0) n = 1.0;
                else if (n < -1.0) n = -1.0;

                final double N = asin(n);
                
                //Compute elliptic integral of the first kind
                final double Xe = NumIntegration.getInEllipticIntegral1(sqrt(0.5), N, 1.0e-14);
                double X = R * Xe + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_adams1 coordinate function, ", "X_adams1 > MAX_FLOAT: ", X);

                return X;
        }

        public static double Y_adams1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = (cos(phi) * sin(0.5 * lonr / RO) - sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in Y_adams1 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = (cos(phi) * sin(0.5 * lonr / RO) + sin(phi)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in Y_adams1 coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                 final double  v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in Y_adams1 coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);

                //Compute elliptic integral of the first kind
                final double Ye = NumIntegration.getInEllipticIntegral1(sqrt(0.5), M, 1.0e-14);
                double Y = R * Ye + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_adams1 coordinate function, ", "Y_adams1 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Adams World in a Square II
        public static double X_adams2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = cos(phi) * sin(0.5 * lonr / RO) ;

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in X_adams2 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = sin(phi);
                final double B = acos(b);
                 final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in X_adams2 coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in X_adams2 coordinate function, ", "abs(n) > 1: ", n);

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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_adams2 coordinate function, ", "X_adams2 > MAX_FLOAT: ", X);

                return X;
        }

        public static double Y_adams2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double phi = asin(tan(0.5 * lat / RO));
                final double a = cos(phi) * sin(0.5 * lonr / RO);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in Y_adams2 coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = sin(phi);
                final double B = acos(b);
                final double v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in Y_adams2 coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in Y_adams2 coordinate function, ", "abs(n) > 1: ", n);

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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_adams2 coordinate function, ", "Y_adams2 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Albers equal area
        public static double X_aea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        { 
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = sin(lat1 / RO) + sin(lat2 / RO);
                final double B = cos(lat1 / RO) * cos(lat1 / RO);
                final double C = B + A * (sin(lat1 / RO) - sin(lat / RO));

                //Throw exception
                if (C < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(C) in X_aea coordinate function, ", "C < 0: ", C);

                final double X =  2.0 * R / (A) * sqrt(C) * sin(A / 2.0 * lonr / RO) + dx;

                //Throw exception
                if (abs (X) > MAX_FLOAT )  
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_aea coordinate function, ", "X_aea > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_aea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double A = sin(lat1 / RO) + sin(lat2 / RO);
                final double B = cos(lat1 / RO) * cos(lat1 / RO);
                final double C = B + A * (sin(lat1 / RO) - sin((lat1 + lat2) / 2.0 / RO));
                final double D = B + A * (sin(lat1 / RO) - sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT  || C <= 0)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_aea coordinate function, ", "1 / A, A = 0:", A);

                //Throw exception
                if (D < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in Y_aea coordinate function, ", "D < 0: ", D);

                final double Y = 2.0 * R / A * sqrt(C) - 2.0 * R / A * sqrt(D) * cos(A / 2.0 * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_aea coordinate function, ", "Y_AEA > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Azimuthal equidistant
        public static double X_aeqd(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * (90 - lat) / RO * sin(lonr/RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_aeqd coordinate function, ", "X_aeqd > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_aeqd(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double Y = -R * (90 - lat) / RO * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_aeqd coordinate function, ", "Y_aeqd > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Aitoff
        public static double X_aitoff(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                double X;
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO) * cos(lonr / 2.0 / RO);

                //Throw exception
                if (abs(A) > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(A) in X_aitoff coordinate function, ", "A > 1: ", A);

                final double B = acos(A);

                if (abs(B) < MIN_FLOAT)
                        X = dx;

                else
                {
                        final double C = sin(lat / RO) / sin(B);
                        double D = 1 - C * C;

                        //Throw exception
                        if (D < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in X_aitoff coordinate function, ", "D < 0: ", D);

                        //Correct D
                        if (D < 0.0) D = 0.0;
                      
                        X = 2.0 * R * B * signum(lonr) * sqrt(D) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_aitoff coordinate function, ", "X_aitoff > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_aitoff(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                double Y;

                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO) * cos(lonr / 2.0 / RO);

                //Throw exception
                if (A > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(A) in Y_aitoff coordinate function, ", "A > 1: ", A);

                final double B = acos(A);

                if (abs(B) < MIN_FLOAT)
                        Y = dy;

                else
                {
                        final double C = sin(lat / RO) / sin(B);

                        Y = R * B * C + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_aitoff coordinate function, ", "Y_aitoff > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Apianus
        public static double X_api(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double F = ((PI / 2) *(PI / 2) * RO / abs(lonr) + abs(lonr) / RO) / 2;
                        final double Y = R * lat / RO;
                        final double G = F * F - Y * Y / (R * R);

                        //Throw exception
                        if (G < 0)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(G) in X_bacon coordinate function, ", "G < 0: ", G);

                        X = R * signum(lonr) * (abs(lonr) / RO - F + sqrt(G)) + dx;
                }
                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_api coordinate function, ", "X_api > MAX_FLOAT: ", X);

                return X;

        }


        public static double Y_api(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_api coordinate function, ", "Y_api > MAX_FLOAT: ", Y);

                return Y;
        }


        //Apianus Elliptical
        public static double X_apiel(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 2.0 * lat / (RO * PI);

                //Throw exception
                if (A > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(A) in X_apiel coordinate function, ", "A > 1: ", A);


                final double X = R * lonr / RO * cos(asin(A)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_apiel coordinate function, ", "X_apiel > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_apiel(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_apiel coordinate function, ", "Y_apiel > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Armadillo
        public static double X_armad(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double lats = -atan(cos(0.5 * lonr / RO) / tan(20 / RO)) * RO;
                final double X =  (lat < lats ? R * (1 + cos(lats / RO)) * sin(lonr / 2.0 / RO) + dx : R * (1 + cos(lat / RO)) * sin(lonr / 2.0 / RO) + dx);

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_armad coordinate function, ", "X_armad > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_armad(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double lats = -atan(cos(0.5 * lonr / RO) / tan(20 / RO)) * RO;
                final double Y = (lat < lats ? R * ((1 + sin(20 / RO) - cos(20 / RO)) / 2.0 + sin(lats / RO) * cos(20 / RO) - (1 + cos(lats / RO)) * sin(20 / RO) * cos(lonr / 2.0 / RO)) + dy :
                                          R * ((1 + sin(20 / RO) - cos(20 / RO)) / 2.0 + sin(lat / RO) * cos(20 / RO) - (1 + cos(lat / RO)) * sin(20 / RO) * cos(lonr / 2.0 / RO)) + dy);

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_armad coordinate function, ", "Y_armad > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //August Epicycloidal
        public static double X_august(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double C1 = sqrt(1 - tan(0.5 * lat / RO) * tan(0.5 * lat / RO));
                final double C = 1 + C1 * cos(0.5 * lonr / RO);

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_august coordinate function, ", "1 / C, C = 0:", C);

                final double X1 = sin(0.5 * lonr / RO) * C1 / C;
                final double Y1 = tan(0.5 * lat / RO) / C;

                final double X = 4.0 * R * X1 * (3.0 + X1 * X1 - 3.0 * Y1 * Y1) / 3.0 + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_august coordinate function, ", "X_armad > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double Y_august(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)        
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double C1 = sqrt(1 - tan(0.5 * lat / RO) * tan(0.5 * lat / RO));
                final double C = 1 + C1 * cos(0.5 * lonr / RO);

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_august coordinate function, ", "1 / C, C = 0:", C);

                final double X1 = sin(0.5 * lonr / RO) * C1 / C;
                final double Y1 = tan(0.5 * lat / RO) / C;

                final double Y = 4.0 * R * Y1 * (3.0 + 3.0 * X1 * X1 - Y1 * Y1) / 3.0 + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_august coordinate function, ", "Y_armad > MAX_FLOAT: ", Y);

                return Y;
        }
             
        
        //Bacon Globular
        public static double X_bacon(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(G) in X_bacon coordinate function, ", "G < 0: ", G);

                        //Correct C2
                        if (G < 0.0) G = 0.0;

                        X = R * signum(lonr) * (abs(lonr) / RO - F + sqrt(G)) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_bacon coordinate function, ", "X_bacon > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_bacon(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * (PI / 2) * sin(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_bacon coordinate function, ", "Y_bacon > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Boggs Eumorphic
        public static double X_boggs(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if (abs(abs(lat) - 90) < MAX_ANGULAR_DIFF)
                {
                        X = dx;
                }

                else
                {
                        final double theta0 = lat;
                        final double theta = NewtonRaphson(Projections::FTheta_moll, Projections::FThetaDer_moll, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                        final double A = 1 / cos(lat / RO) + 1.11072 / cos(theta / RO);

                        //Throw exception
                        if (abs(A) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_boggs coordinate function, ", "1 / A, A = 0:", A);

                        X = 2.00276 * R * lonr / RO / A + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_boggs coordinate function, ", "X_boggs > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double Y_boggs(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                if (abs(abs(lat) - 90) < MAX_ANGULAR_DIFF)
                {
                        Y = 0.49931 * R * (lat / RO + sqrt(2)) + dy;
                }

                else
                {
                        final double theta0 = lat;
                        final double theta = NewtonRaphson(Projections::FTheta_moll, Projections::FThetaDer_moll, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                        Y = 0.49931 * R * (lat / RO + sqrt(2) * sin(theta / RO)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_boggs coordinate function, ", "Y_boggs > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Bonne
        public static double X_bonne(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(lat1) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat1) in X_bonne coordinate function, ", "lat1 = +-90: ", lat1);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_bonne coordinate function, ", "1 / A, A = 0:", A);

                final double B = 1 / A + (lat1 - lat) / RO;
                final double X = R * B * sin(lonr / RO * cos(lat / RO) / B) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_bonne coordinate function, ", "X_bonne > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_bonne(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(lat1) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat1) in Y_bonne coordinate function, ", "lat1 = +-90: ", lat1);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_bonne coordinate function, ", "1 / A, A = 0:", A);

                final double B = 1 / A + (lat1 - lat) / RO;

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_bonne coordinate function, ", "1 / B, B = 0:", B);

                final double Y = R * 1 / A - R * B * cos(lonr / RO * cos(lat / RO) / B) + dy;

                return Y;
        }


        //Breusignum
        public static double X_breus(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2.0 / RO;
                final double B = cos(A);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in X_breus coordinate function, ", "B < 0: ", B);

                final double X = 2.0 * R * sin(A) * sqrt(B) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_breus coordinate function, ", "X_breus > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_breus(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2.0 / RO;
                final double B = cos(A);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in Y_breus coordinate function, ", "B < 0: ", B);

                final double Y = -2.0 * R * sin(A) * sqrt(B) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_breus coordinate function, ", "Y_breus > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Central Cylindrical
        public static double X_cc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr / RO * cos (lat1 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_cc coordinate function, ", "X_cc > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double Y_cc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(abs(lat) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in Y_cc coordinate function, ", "lat = +-90: ", lat);

                final double Y = R * tan(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_cc coordinate function, ", "Y_cc > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Cylindrical Equal Area
        public static double X_cea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_cea coordinate function, ", "X_cea > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_cea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * sin(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_cea coordinate function, ", "Y_cea > MAX_FLOAT: ", Y);

                return Y;
        }


        //Clark Perspective
        public static double X_clar(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * (1.368 + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_clar coordinate function, ", "1 / A, A = 0:", A);

                final double X = R * 2.368 * R * cos(lat / RO) / A * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_clar coordinate function, ", "X_clar > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_clar(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * (1.368 + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_clar coordinate function, ", "1 / A, A = 0:", A);

                final double Y = -R * 2.368 * R * cos(lat / RO) / A * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_clar coordinate function, ", "Y_clar > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Collignon
        public static double X_collg(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * 2.0 / sqrt(PI) * lonr / RO * sqrt(1 - sin(lat / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_collg coordinate function, ", "X_collg > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_collg(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * sqrt(PI) * (1 - sqrt(1 - sin(lat / RO))) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_collg coordinate function, ", "Y_collg > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Craster Parabolic
        public static double X_crast(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * sqrt(3.0 / PI) * lonr / RO * (2.0 * cos(2.0 * lat / 3.0 / RO) - 1) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_crast coordinate function, ", "X_crast > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_crast(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * sqrt(3.0 * PI) * sin(lat / 3.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_crast coordinate function, ", "Y_crast > MAX_FLOAT: ", Y);

                return Y;
        }



        //Conformal world in ellipse
        public static double X_cwe(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in X_cwe coordinate function, ", "D < 0: ", D);

                //Correct D
                if (D < 0.0) D = 0.0;

                final double R1 = A - sqrt(D);

                // Throw exception
                if (abs(kc) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate X_cwe coordinate function, ", "1.0 / kc, kc = 0: ", kc);

                // Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate X_cwe coordinate function, ", "1.0 / C, C = 0: ", C);

                double R2 = R1 / (2 * C * kc);

                //Throw exception
                if (R2 > 1.0 + ARGUMENT_ROUND_ERROR || R2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(R2) in X_cwe coordinate function, ", "abs(R2) > 1: ", R2);

                //Correct R2
                if (R2 > 1.0) R2 = 1.0;
                else if (R2 < -1.0) R2 = -1.0;

                final double lambda = -asin(R2);

                // Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate X_cwe coordinate function, ", "1.0 / B, B = 0: ", B);

                double E = 1 - R1 * R1 / (4 * B * B);

                //Throw exception
                if (E < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(E) in X_cwe coordinate function, ", "E < 0: ", E);

                //Correct m
                if (E < 0.0) E = 0.0;

                // Throw exception
                if (abs(ks) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate X_cwe coordinate function, ", "1.0 / ks, ks = 0: ", ks);

                double F = sqrt(E) / ks;

                //Throw exception
                if (F > 1.0 + ARGUMENT_ROUND_ERROR || F < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(F) in X_cwe coordinate function, ", "abs(F) > 1: ", F);

                //Correct E
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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_cwe coordinate function, ", "X_cwe > MAX_FLOAT: ", X);

                return X;
        }

        public static double Y_cwe(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(D) in Y_cwe coordinate function, ", "D < 0: ", D);

                //Correct D
                if (D < 0.0) D = 0.0;

                final double R1 = A - sqrt(D);

                // Throw exception
                if (abs(kc) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_cwe coordinate function, ", "1.0 / kc, kc = 0: ", kc);

                // Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_cwe coordinate function, ", "1.0 / C, C = 0: ", C);

                double R2 = R1 / (2 * C * kc);

                //Throw exception
                if (R2 > 1.0 + ARGUMENT_ROUND_ERROR || R2 < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(R2) in Y_cwe coordinate function, ", "abs(R2) > 1: ", R2);

                //Correct R2
                if (R2 > 1.0) R2 = 1.0;
                else if (R2 < -1.0) R2 = -1.0;

                final double lambda = -asin(R2);

                // Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_cwe coordinate function, ", "1.0 / B, B = 0: ", B);

                double E = 1 - R1 * R1 / (4 * B * B);

                //Throw exception
                if (E < -ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(E) in Y_cwe coordinate function, ", "E < 0: ", E);

                //Correct m
                if (E < 0.0) E = 0.0;

                // Throw exception
                if (abs(ks) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_cwe coordinate function, ", "1.0 / ks, ks = 0: ", ks);

                double F = sqrt(E) / ks;

                //Throw exception
                if (F > 1.0 + ARGUMENT_ROUND_ERROR || F < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(F) in Y_cwe coordinate function, ", "abs(F) > 1: ", F);

                //Correct E
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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_cwe coordinate function, ", "Y_cwe > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Denoyer Semi-Elliptical
        public static double X_denoy(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X =  R * lonr / RO * cos((0.95 - 1 / 12 * abs(lonr) / RO + 1 / 600 * pow((abs(lonr) / RO), 3)) * lat / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_denoy coordinate function, ", "X_denoy > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_denoy(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_denoy coordinate function, ", "Y_denoy > MAX_FLOAT: ", Y);

                return Y;
        }


        //Eckert I
        public static double X_eck1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 2.0 * sqrt(2.0 / (3.0 * PI)) * R * lonr / RO * (1 - abs(lat / (PI * RO))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eck1 coordinate function, ", "X_eck1 > MAX_FLOAT: ", X);

                return X;

        }


        public static double Y_eck1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * sqrt(2.0 / (3.0 * PI)) * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eck1 coordinate function, ", "Y_eck1 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Eckert II
        public static double X_eck2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 2.0 * R / sqrt(6.0 * PI) * lonr / RO * sqrt(4.0 - 3.0 * sin(abs(lat / RO))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eck2 coordinate function, ", "X_eck2 > MAX_FLOAT: ", X);

                return X;

        }


        public static double Y_eck2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = sqrt(2.0 * PI / 3) * R* (2.0 - sqrt(4.0 - 3.0 * sin(abs(lat / RO)))) * signum(lat) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eck2 coordinate function, ", "Y_eck2 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Eckert III
        public static double X_eck3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 1 - 4.0 * pow((lat / (PI * RO)), 2);

                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in X_eck3 coordinate function, ", "A < 0: ", A);

                final double X = 2.0 / sqrt(PI * (4.0 + PI)) * R * (1 + sqrt(A)) * lonr / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eck3 coordinate function, ", "X_eck3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eck3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 4.0 / sqrt(PI * (4.0 + PI)) * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eck3 coordinate function, ", "Y_eck3 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Eckert IV
        public static double X_eck4(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double latr = lat / RO;
                final double theta0 = (0.895168 * latr + 0.0218849 * latr * latr * latr + 0.00806809 * latr * latr * latr * latr * latr) * RO;

                final double theta = NewtonRaphson(Projections::FTheta_eck4, Projections::FThetaDer_eck4, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 2.0 * R * lonr / RO * (1 + cos(theta / RO)) / sqrt(PI * (4.0 + PI)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eck4 coordinate function, ", "X_eck4 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eck4(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double latr = lat / RO;
                final double theta0 = (0.895168 * latr + 0.0218849 * latr * latr * latr + 0.00806809 * latr * latr * latr * latr * latr) * RO;
                
                final double theta = NewtonRaphson(Projections::FTheta_eck4, Projections::FThetaDer_eck4, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 2.0 * R * sqrt(PI / (4.0 + PI)) * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eck4 coordinate function, ", "Y_eck4 > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_eck4(final double lat, final double theta)
        {
                return theta / RO + 0.5 * sin(2.0 * theta / RO) + 2.0 * sin(theta / RO) - (2.0 + PI / 2) * sin(lat / RO);
        }


        public static double FThetaDer_eck4(final double lat, final double theta)
        {
                return (1 + cos(2.0 * theta / RO) + 2.0 * cos(theta / RO))/RO;
        }


        //Eckert V
        public static double X_eck5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr / RO * (1 + cos(lat / RO)) / sqrt(2.0 + PI) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eck5 coordinate function, ", "X_eck5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eck5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * R * lat / RO / sqrt(2.0 + PI) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eck5 coordinate function, ", "Y_eck5 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Eckert VI
        public static double X_eck6(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_eck6, Projections::FThetaDer_eck6, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = R * (1 + cos(theta / RO)) * lonr / RO / sqrt(2.0 + PI) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eck6 coordinate function, ", "X_eck6 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eck6(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_eck6, Projections::FThetaDer_eck6, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 2.0 * R * theta / sqrt(2.0 + PI) / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eck6 coordinate function, ", "Y_eck6 > MAX_FLOAT: ", Y);

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


        //Eisenlohr
        public static double X_eisen(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double S1 = sin(0.5 * lonr / RO);
                final double C1 = cos(0.5 * lonr / RO);
                final double D = cos(0.5 * lat / RO) + C1 * sqrt(2.0 * cos(lat / RO));

                //Throw exception
                if (abs(D) < MIN_FLOAT)
                        throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate X_eisen coordinate function, ", "1 / D, D = 0:", D);

                final double TT = sin(0.5 * lat / RO) / D;
                final double C = sqrt(2.0 / (1 + TT * TT));
                final double E = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 + S1);
                final double F = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 - S1);

                //Throw exception
                if (abs(F) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_eisen coordinate function, ", "1 / E, E = 0:", E);

                final double G = E / F;

                if (G < 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(G) in X_eisen coordinate function, ", "G < 0: ", G);

                final double V = sqrt(G);

                //Throw exception
                if (abs(V) < MIN_FLOAT)
                        throw new  MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_eisen coordinate function, ", "1 / V, V = 0:", V);

                final double X = R * (3.0 + sqrt(8))*(-2.0 * log(V) + C * (V - 1.0 / V)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_eisen coordinate function, ", "X_eisen > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eisen(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double S1 = sin(0.5 * lonr / RO);
                final double C1 = cos(0.5 * lonr / RO);
                final double D = cos(0.5 * lat / RO) + C1 * sqrt(2.0 * cos(lat / RO));

                //Throw exception
                if (abs(D) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate Y_eisen coordinate function, ", "1 / D, D = 0:", D);

                final double TT = sin(0.5 * lat / RO) / D;
                final double C = sqrt(2.0 / (1 + TT * TT));
                final double E = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 + S1);
                final double F = cos(0.5 * lat / RO) + sqrt(0.5 * cos(lat / RO)) * (C1 - S1);

                //Throw exception
                if (abs(F) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate Y_eisen coordinate function, ", "1 / E, E = 0:", E);

                final double G = E / F;

                //Throw exception
                if (G < 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(G) in Y_eisen coordinate function, ", "G < 0: ", G);

                final double V = sqrt(G);

                //Throw exception
                if (abs(V) < MIN_FLOAT)
                        throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate Y_eisen coordinate function, ", "1 / V, V = 0:", V);

                //Throw exception
                if ((abs(TT)% 90 < MIN_FLOAT) && (abs(TT) > MIN_FLOAT))
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate atan(TT) in Y_eisen coordinate function, ", "TT = PI: ", TT);

                final double Y = R * (3.0 + sqrt(8)) * (-2.0 * atan(TT) + C * TT * (V + 1.0 / V)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eisen coordinate function, ", "Y_eisen > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Equidistant Cylindrical 
        public static double X_eqc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eqc coordinate function, ", "X_eqc > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eqc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eqc coordinate function, ", "Y_eqc > MAX_FLOAT: ", Y);

                return Y;
        }


        //Equidistant Conic (true parallel lat1)
        public static double X_eqdc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_eqdc coordinate function, ", "1 / A, A = 0:", A);

                final double X = (R / A + R * (lat1 - lat) / RO) * sin(sin(lat1 / RO) * lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eqdc coordinate function, ", "X_eqdc > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eqdc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_eqdc coordinate function, ", "1 / A, A = 0:", A);

                final double Y = R / A - (R / A + R * (lat1 - lat) / RO) * cos(sin(lat1 / RO) * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eqdc coordinate function, ", "Y_eqdc > MAX_FLOAT: ", Y);

                return Y;
        }


        //Equidistant Conic (true parallels lat1, lat2)
        public static double X_eqdc2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat1 - 90) < MAX_ANGULAR_DIFF)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_eqdc2 coordinate function, ", "1 / (90 - lat1), lat1 = 90.", lat1);

                final double X = (R * (90 - lat) / RO) * sin(cos(lat1 / RO) / (90 - lat1) * lonr) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eqdc2 coordinate function, ", "X_eqdc2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eqdc2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat1 - 90) < MAX_ANGULAR_DIFF)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_eqdc2 coordinate function, ", "1 / (90 - lat1), lat1 = 90.", lat1);

                final double Y = -(R * (90 - lat) / RO) * cos(cos(lat1 / RO) / (90 - lat1) * lonr) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eqdc2 coordinate function, ", "Y_eqdc2 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Equidistant Conic (true parallel lat1, pole = point)
        public static double X_eqdc3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat1 / RO) - cos(lat2 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_eqdc3 coordinate function, ", "1 / A, A = 0:", A);

                final double B = A / (lat2 - lat1) * RO;

                final double X = R * ((lat2 / RO * cos(lat1 / RO) - lat1 / RO * cos(lat2 / RO)) / A - lat / RO) * sin( B * lonr/RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_eqdc3 coordinate function, ", "X_eqdc3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_eqdc3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat1 / RO) - cos(lat2 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_eqdc3 coordinate function, ", "1 / A, A = 0:", A);

                final double B = A / (lat2 - lat1) * RO;

                final double Y = -R * ((lat2 / RO * cos(lat1 / RO) - lat1 / RO * cos(lat2 / RO)) / A - lat / RO) * cos(B * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_eqdc3 coordinate function, ", "Y_eqdc3 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Fahey
        public static double X_fahey(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat / 2.0 / RO);
                final double B = 1 - pow(A, 2);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in X_fahey coordinate function, ", "B < 0: ", B);

                final double X = R * lonr / RO * cos(lat1 / RO) * sqrt(B) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_fahey coordinate function, ", "X_fahey > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_fahey(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * (1 + cos(lat1 / RO)) * tan(lat / 2.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_fahey coordinate function, ", "Y_fahey > MAX_FLOAT: ", Y);

                return Y;
        }


        //Foucaut Sine-Tangent
        public static double X_fouc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 2.0 / sqrt(PI) * R * lonr / RO * cos(lat / RO) * pow((cos(lat / 2.0 / RO)), 2) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_fouc coordinate function, ", "X_fouc > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_fouc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = sqrt(PI) * R * tan(lat / 2.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_fouc coordinate function, ", "Y_fouc > MAX_FLOAT: ", Y);

                return Y;
        }


        //Foucaut Sinusoidal
        public static double X_fouc_s(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO);
                final double B = sin(lat1 / RO);
                final double C = B + (1 - B) * A;

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_fouc_s coordinate function, ", "1 / C, C = 0:", A);

                final double X = R * lonr / RO * A / C + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_fouc_s coordinate function, ", "X_fouc_s > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_fouc_s(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = sin(lat1 / RO);
                final double Y = R * (A * lat / RO + (1 - A) * sin(lat / RO)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_foucs coordinate function, ", "Y_foucs > MAX_FLOAT: ", Y);

                return Y;
        }


        //Fournier I Globular
        public static double X_fourn(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double Y = Y_fourn(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                        double N = 1 - (2.0 * Y / (PI  * R)) * (2.0 * Y / (PI  * R));

                        //Throw exception
                        if (N < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(N) in X_fourn coordinate function, ", "N < 0: ", N);

                        //Correct N
                        if (N < 0.0) N = 0.0;
                        
                        X = R * lonr / RO  * sqrt(N) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_fourn coordinate function, ", "X_fourn > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_fourn(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_fourn coordinate function, ", "1 / Q, Q = 0.", Q);

                        final double F = ((C - (lat / RO) * (lat / RO)) / Q);
                        final double G = 2.0 * lonr / (PI * RO);
                        final double L = (G * G - 1);

                        // Throw exception
                        if (abs(L) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_fourn coordinate function, ", "1 / L, L = 0.", L);

                        double M = F * F - L * (C - F * P - (lonr / RO) * (lonr / RO));

                        //Throw exception
                        if (M < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(M) in Y_fourn coordinate function, ", "M < 0: ", M);

                        //Correct M
                        if (M < 0.0) M = 0.0;
                        
                        Y = R / L  * signum(lat) * (sqrt(M) - F) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_fourn coordinate function, ", "Y_fourn > MAX_FLOAT: ", Y);

                return Y;
        }


        //Fournier II Elliptical
        public static double X_fourn2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R / sqrt(PI) * lonr / RO * cos(lat / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_fourn2 coordinate function, ", "X_fourn2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_fourn2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * sqrt(PI) / 2.0 * sin(lat / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_fourn2 coordinate function, ", "Y_fourn2 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Gall Stereographic
        public static double X_gall(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * cos(lat1 / RO) * lonr / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_gall coordinate function, ", "X_gall > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_gall(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                //Throw exception
                if (abs(lat/2) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat/2) in X_fourn coordinate function, ", "lat/2.0 = +-90: ", lat);

                final double Y = R * (1 + cos(lat1 / RO)) * tan(lat / 2.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_gall coordinate function, ", "Y_gall > MAX_FLOAT: ", Y);

                return Y;
        }


        //Ginsburg VIII (TsNIIGAiK)
        public static double X_gins8(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr / RO * (1 - 0.162388 * (lat / RO) * (lat / RO)) * (0.87 - 0.000952426 * pow((lonr / RO), 4)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_gins8 coordinate function, ", "X_gins8 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_gins8(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO * (1 + 1 / 12 * pow((abs(lat) / RO), 3)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_gins8 coordinate function, ", "Y_gins8 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Gnomonic
        public static double X_gnom(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                //if (lat <= 0)
                //        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in X_gnom coordinate function, ", "lat <= 0: ", lat);

                final double X = R * tan((90 - lat) / RO) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_gnom coordinate function, ", "X_gnom > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_gnom(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                //if (lat <= 0)
                //        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in Y_gnom coordinate function, ", "lat <= 0: ", lat);

                final double Y = -R * tan((90 - lat) / RO) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_gnom coordinate function, ", "Y_gnom > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Goode Homolosine
        public static double X_goode(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lat_int = 40 + 44.0 / 60;
                double X = 0;

                if (abs(lat) <= lat_int)
                {
                        X = X_sinu(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                }

                else
                {
                        X = X_moll(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_goode coordinate function, ", "X_goode > MAX_FLOAT: ", X);

                return X;
        }

        public static double Y_goode(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lat_int = 40 + 44.0 / 60;
                double Y = 0;

                if (abs(lat) <= lat_int)
                {
                        Y = Y_sinu(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                }

                else
                {
                        final double theta0 = lat;
                        final double theta = NewtonRaphson(Projections::FTheta_moll, Projections::FThetaDer_moll, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                        Y = R * (sqrt(2) * sin(theta / RO) - 0.0528 * signum(lat)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_goode coordinate function, ", "Y_goode > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Guyou
        public static double X_guyou(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in X_guyou coordinate function, ", "abs(a) > 1: ", a);

                        final double A = acos(a);
                        final double b = (cos (lat / RO) * sin (lonr / RO) + sin(lat / RO)) / sqrt(2.0); 

                        //Throw exception
                        if (abs(b) > 1.0)
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in X_guyou coordinate function, ", "abs(b) > 1: ", b);

                        final double B = acos(b);
                                                final double u = 0.5 * (A + B);
                        double n = sqrt(2) * cos(u);

                        //Throw exception
                        if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in X_guyou coordinate function, ", "abs(n) > 1: ", n);

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
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_guyou coordinate function, ", "X_guyou > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double Y_guyou(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in Y_guyou coordinate function, ", "abs(a) > 1: ", a);

                        final double A = acos(a);
                        final double b = (cos (lat / RO) * sin (lonr / RO) + sin(lat / RO)) / sqrt(2.0); 

                        //Throw exception
                        if (abs(b) > 1.0)
                                 throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in Y_guyou coordinate function, ", "abs(b) > 1: ", b);

                        final double B = acos(b);
                        final double  v = 0.5 * (A - B);
                        double m = sqrt(2) * sin(v);

                        //Throw exception
                        if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in Y_guyou coordinate function, ", "abs(m) > 1: ", m);

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
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_guyou coordinate function, ", "Y_guyou > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Hammer
        public static double X_hammer(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 2.0 * R * cos(lat / RO) * sin(lonr / 2.0 / RO) * sqrt(2) / (sqrt(1 + cos(lat / RO) * cos(lonr / 2.0 / RO))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_hammer coordinate function, ", "X_hammer > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_hammer(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double Y = 2.0 * R * sin(lat / RO) / (sqrt(2) * sqrt(1 + cos(lat / RO) * cos(lonr / 2.0 / RO))) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_hammer coordinate function, ", "Y_hammer > MAX_FLOAT: ", Y);

                return Y;
        }


        //Hatano Asymmetrical Equal Area
        public static double X_hataea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = 0.5 * lat;
                final double theta = NewtonRaphson(Projections::FTheta_hataea, Projections::FThetaDer_hataea, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 0.85 * R * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_hataea coordinate function, ", "X_hataea > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_hataea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = 0.5 * lat;

                final double theta = NewtonRaphson(Projections::FTheta_hataea, Projections::FThetaDer_hataea, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = ( lat < 0 ? 1.93052 * R * sin(theta / RO) + dy : 1.56548 * R * sin(theta / RO) + dy );

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_hataea coordinate function, ", "Y_hataea > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_hataea(final double lat, final double theta)
        {
                return ( lat < 0 ? 2.0 * theta / RO + sin(2.0 * theta / RO) - 2.43763 * sin(lat / RO) : 2.0 * theta / RO + sin(2.0 * theta / RO) - 2.67595 * sin(lat / RO));
        }


        public static double FThetaDer_hataea(final double lat, final double theta)
        {
                return (2.0 + 2.0 * cos(2.0 * theta / RO)) / RO;
        }


        //La Hire
        public static double X_hire(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * (2.0 + sqrt(2) / 2) * R * cos(lat / RO) / (R * (1 + sqrt(2) / 2) + R * sin(lat / RO)) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_hire coordinate function, ", "X_hire > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_hire(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double Y = -R * (2.0 + sqrt(2) / 2) * R * cos(lat / RO) / (R * (1 + sqrt(2) / 2) + R * sin(lat / RO)) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_hire coordinate function, ", "Y_hire > MAX_FLOAT: ", Y);

                return Y;
        }


        //James Perspective
        public static double X_jam(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * (1.5 + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_jam coordinate function, ", "1 / A, A = 0:", A);

                final double X = R * 2.5 * R * cos(lat / RO) / A * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_jam coordinate function, ", "X_jam > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_jam(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * (1.5 + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_jam coordinate function, ", "1 / A, A = 0:", A);

                final double Y = -R * 2.5 * R * cos(lat / RO) / A * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_jam coordinate function, ", "Y_jam > MAX_FLOAT: ", Y);

                return Y;
        }


        //Kavrayskiy V
        public static double X_kav5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 1.35439 / 1.50488 * R * lonr / RO * cos(lat / RO) / cos(lat / 1.35439 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_kav5 coordinate function, ", "X_kav5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_kav5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 1.50488 * R * sin(lat / 1.35439 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_kav5 coordinate function, ", "Y_kav5 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Kavrayskiy VII
        public static double X_kav7(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 1 - 3.0 * pow((lat / (PI * RO)), 2);

                //Throw exception
                if (abs(A) == MIN_FLOAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in X_kav7 coordinate function, ", "A = 0: ", A);

                final double X = sqrt(3) / 2.0 * R * sqrt(A) * lonr / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_kav7 coordinate function, ", "X_kav7 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_kav7(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_kav7 coordinate function, ", "Y_kav7 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Lambert Azimuthal Equal Area
        public static double X_laea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 2.0 * R * sin((90 - lat) / 2.0 / RO) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_laea coordinate function, ", "X_laea > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_laea(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double Y = -2.0 * R * sin((90 - lat) / 2.0 / RO) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_laea coordinate function, ", "Y_laea > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Lagrange Conformal
        public static double X_lagrng(double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate X_lagrng coordinate function, ", "1 / A1, A1 = 0:", A1);

                        final double V = A / A1;
                        final double C = 0.5 * (V + 1.0 / V) + cos(lonr / W / RO);

                        //Throw exception
                        if (abs(C) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate X_lagrng coordinate function, ", "1 / C, C = 0:", C);

                        X = 2.0 * R * sin(lonr / W / RO) / C + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_lagrng coordinate function, ", "X_larr > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_lagrng (final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_lagrng coordinate function, ", "1 / A1, A1 = 0:", A1);

                        final double V = A / A1;
                        final double C = 0.5 * (V + 1.0 / V) + cos(lonr / W / RO);

                        //Throw exception
                        if (abs(C) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_lagrng coordinate function, ", "1 / C, C = 0:", C);

                        //Throw exception
                        if (abs(V) < MIN_FLOAT)
                                throw new MathZeroDevisionException ("MathZeroDevisionException: can not evaluate Y_lagrng coordinate function, ", "1 / V, V = 0:", V);

                        Y = R * (V - 1.0 / V) / C + dy;
                }


                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_lagrng coordinate function, ", "Y_lagrng > MAX_FLOAT: ", Y);

                return Y;
        }


        //Larrivee
        public static double X_larr(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / RO);

                if (A < 0)
                        throw new MathInvalidArgumentException  ("MathInvalidArgumentException: can not evaluate sqrt(A) in X_larr coordinate function, ", "A < 0: ", A);

                final double X = 0.5 * R * lonr / RO * (1 + sqrt(A)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_larr coordinate function, ", "X_larr > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_larr(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = cos(lat / 2.0 / RO) * cos(lonr / 6 / RO);

                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_larr coordinate function, ", "1 / A, A = 0:", A);

                
                final double Y = R * lat / RO / A  + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_larr coordinate function, ", "Y_larr > MAX_FLOAT: ", Y);

                return Y;
        }


        //Laskowski
        public static double X_lask(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * (0.975534 * lonr / RO - 0.119161 * (lonr / RO) * (lat / RO) * (lat / RO) - 0.0143059 * pow((lonr / RO), 3) * (lat / RO) * (lat / RO) -
                        0.0547009 * (lonr / RO) * pow((lat / RO), 4)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_lask coordinate function, ", "X_lask > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_lask(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double Y = R * (1.00384 * (lat / RO) + 0.0802894 * (lonr / RO) * (lonr / RO) * (lat / RO) + 0.0998909 * pow((lat / RO), 3) + 
                        0.000199025 * pow((lonr / RO), 4) * (lat / RO) - 0.0285500 * (lonr / RO) * (lonr / RO) * pow((lat / RO), 3) - 0.0491032 * pow((lat / RO), 5)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_lask coordinate function, ", "Y_lask > MAX_FLOAT: ", Y);

                return Y;
        }


        //Lambert Conformal Conic
        public static double X_lcc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = sin(lat1 / RO);
                final double X = R / tan(lat1 / RO) * pow((tan((lat1 / 2.0 + 45) / RO) / tan((lat / 2.0 + 45) / RO)), (A)) * sin(A * lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_lcc coordinate function, ", "X_lcc > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_lcc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_lcc coordinate function, ", "1 / A, A = 0:", A);

                final double B = sin(lat1 / RO);
                final double Y = R / A - R / A * pow((tan((lat1 / 2.0 + 45) / RO) / tan((lat / 2.0 + 45) / RO)), B) * cos(B * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_lcc coordinate function, ", "Y_lcc > MAX_FLOAT: ", Y);

                return Y;
        }


        //Lambert Equal Area Conic (standard parallel lat1)
        public static double X_leac(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_leac coordinate function, ", "1 / A, A = 0:", A);

                final double B = sin(lat1 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_leac coordinate function, ", "1 / B, B = 0:", B);

                final double X = sqrt(R / A * R / A + 2.0 * R * R / B * (B - sin(lat / RO))) * sin(B * lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_leac coordinate function, ", "X_leac > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_leac(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = tan(lat1 / RO);

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_leac coordinate function, ", "1 / A, A = 0:", A);

                final double B = sin(lat1 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_leac coordinate function, ", "1 / B, B = 0:", B);

                final double Y = R / A - sqrt(R / A * R / A + 2.0 * R * R / B * (B - sin(lat / RO))) * cos(B * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_leac coordinate function, ", "Y_leac > MAX_FLOAT: ", Y);

                return Y;
        }


        //Lambert Equal Area Conic (standard parallels lat1, lat2)
        public static double X_leac2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = sin(lat1 / RO);

                //Throw exception
                if (abs(A + 1) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_leac2 coordinate function, ", "1 / A, A = 0:", A);

                final double B = 2.0 / ((1 + A) / 2) * (1 - sin(lat / RO));

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in X_leac2 coordinate function, ", "A < 0: ", B);

                final double X = R * sqrt(B) * sin((1 + A) / 2.0 * lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_leac2 coordinate function, ", "X_leac2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_leac2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = sin(lat1 / RO);

                //Throw exception
                if (abs(A + 1) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_leac2 coordinate function, ", "1 / A, A = 0:", A);

                final double B = (1 - A) / (1 + A);

                //Throw exception
                if (B < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(B) in Y_leac2 coordinate function, ", "B < 0: ", B);

                final double C = 2.0 / ((1 + A) / 2) * (1 - sin(lat / RO));

                //Throw exception
                if (C < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(C) in Y_leac2 coordinate function, ", "C < 0: ", C);

                final double Y = 2.0 * R * sqrt(B) - R * sqrt(C) * cos((1 + A) / 2.0 * lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_leac2 coordinate function, ", "Y_leac2 > MAX_FLOAT: ", Y);

                return Y;
        }
         
          
        //Littrow
        public static double X_litt(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in X_litt coordinate function, ", "lat = +-90: ", lat);
                
                final double X = R * sin(lonr / RO) / cos(lat / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_litt coordinate function, ", "X_litt > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_litt(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
               final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                if (abs(lat) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in Y_litt coordinate function, ", "lat = +-90: ", lat);
                
                final double Y = R * tan(lat / RO) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_litt coordinate function, ", "Y_litt > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Loximuthal
        public static double X_loxim(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_loxim coordinate function, ", "X_loxim > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double Y_loxim(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double  Y = R * (lat - lat1) / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_loxim coordinate function, ", "Y_loxim > MAX_FLOAT: ", Y);

                return Y;
        }


        //McBryde-Thomas Sine I.
        public static double X_mbt_s(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 1.36509 / 1.48875 * R * lonr / RO * cos(lat / RO) / cos(lat / 1.36509 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_mbt_s coordinate function, ", "X_mbt_s > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_mbt_s(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 1.48875 * R * sin(lat / 1.36509 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_mbt_s coordinate function, ", "Y_mbt_s > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //McBryde-Thomas Flat-Pole Sine III
        public static double X_mbt_s3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)        
        {
                final double lat_int = 55 + 51.0 / 60;
                double X = 0;

                if (abs(lat) <= lat_int)
                {
                        X = X_sinu(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                }

                else
                {
                        X = X_mbtfps(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_mbt_s3 coordinate function, ", "X_mbt_s3 > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double Y_mbt_s3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)        
        {
                final double lat_int = 55 + 51.0 / 60;
                double Y = 0;
                
                if (abs(lat) <= lat_int)
                {
                        Y = Y_sinu(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                }

                else
                {
                        final double theta0 = lat;
                        final double theta = NewtonRaphson(Projections::FTheta_mbtfps, Projections::FThetaDer_mbtfps, lat, theta0,  MAX_NR_ITERATIONS, MAX_NR_ERROR);

                        Y = R * (sqrt(6 / (4.0 + PI)) * theta / RO - 0.069065 * signum(lat)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_mbt_s3 coordinate function, ", "Y_mbt_s3 > MAX_FLOAT: ", Y);

                return Y;
        }


        //McBryde-Thomas Flat-Pole Quartic IV
        public static double X_mbtfpq(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_mbtfpq, Projections::FThetaDer_mbtfpq, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = R * lonr / RO * (1 + 2.0 * cos(theta / RO) / cos(0.5 * theta / RO)) / sqrt(3.0 * sqrt(2) + 6) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_mbtfpq coordinate function, ", "X_mbtfpq > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_mbtfpq(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_mbtfpq, Projections::FThetaDer_mbtfpq, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 2.0 * sqrt(3) * R * sin(theta / 2.0 / RO) / sqrt(2.0 + sqrt(2)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_mbtfpq coordinate function, ", "Y_mbtfpq > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_mbtfpq(final double lat, final double theta)
        {
                return sin(theta / 2.0 / RO) + sin(theta / RO) - (1 + sqrt(2) / 2) * sin(lat / RO);
        }


        public static double FThetaDer_mbtfpq(final double lat, final double theta)
        {
                return ( 0.5 * cos(theta / 2.0 / RO) + cos(theta / RO)) / RO;
        }


        //McBryde-Thomas Flat-Pole Sine II
        public static double X_mbtfps(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_mbtfps, Projections::FThetaDer_mbtfps, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = R * sqrt(6 / (4.0 + PI)) * (0.5 + cos(theta / RO)) * lonr / RO / 1.5 + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_mbtfps coordinate function, ", "X_mbtfps > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_mbtfps(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_mbtfps, Projections::FThetaDer_mbtfps, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = R * sqrt(6 / (4.0 + PI)) * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_mbtfs coordinate function, ", "Y_mbtfps > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_mbtfps(final double lat, final double theta)
        {
                return theta / 2.0 / RO + sin(theta / RO) - (1 + PI / 4) * sin(lat / RO);
        }


        public static double FThetaDer_mbtfps(final double lat, final double theta)
        {
                return ( 0.5 + cos(theta / RO)) / RO;
        }


        //Mercator
        public static double X_merc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_merc coordinate function, ", "X_merc > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_merc(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = tan((lat / 2.0 + 45) / RO);

                //Throw exception
                if (abs(lat) == MAX_LAT)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in Y_merc coordinate function, ", "lat = +-90: ", lat);

                //Throw exception
                if (A <= 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate log(A) in Y_merc coordinate function, ", "A <= 0: ", A);

                final double Y = R * log(A) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_merc coordinate function, ", "Y_merc > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Miller Cylindrical
        public static double X_mill(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat1 / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_mill coordinate function, ", "X_mill > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_mill(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = tan((0.4 * lat + 45) / RO);

                //Throw exception
                if (A <= 0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate log(A) in Y_mill coordinate function, ", "A <= 0: ", A);

                final double Y = R * log(A) / 0.8 + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_mill coordinate function, ", "Y_mill > MAX_FLOAT: ", Y);

                return Y;
        }


        //Mollweide
        public static double X_moll(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_moll, Projections::FThetaDer_moll, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 2.0 * R * lonr / RO * sqrt(2) * cos(theta / RO) / PI + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_moll coordinate function, ", "X_moll > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_moll(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_moll, Projections::FThetaDer_moll, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = R * sqrt(2) * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_moll coordinate function, ", "Y_moll > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_moll(final double lat, final double theta)
        {
                return 2.0 * theta / RO + sin(2.0 * theta / RO) - PI * sin(lat / RO);
        }


        public static double FThetaDer_moll(final double lat, final double theta)
        {
                return ( 2.0 + 2.0 * cos(2.0 * theta / RO)) / RO;
        }

        
        //Nell
        public static double X_nell(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_nell, Projections::FThetaDer_nell, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = R * 0.5 * lonr / RO * (1 + cos(theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_nell coordinate function, ", "X_nell > MAX_FLOAT: ", X);

                return X;
        }

        public static double Y_nell(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_nell, Projections::FThetaDer_nell, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = R * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_nell coordinate function, ", "Y_nell > MAX_FLOAT: ", Y);

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


        //Nell-Hammer
        public static double X_nell_h(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 0.5 * R * lonr / RO * (1 + cos(lat / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_nell_h coordinate function, ", "X_nell_h > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_nell_h(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * R * (lat / RO - tan(lat / 2.0 / RO)) + dy;

                //Throw exception
                if (abs(abs(lat) - 2.0 * MAX_LAT) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in X_nell_h coordinate function, ", "lat = +-180: ", lat);

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_nell_h coordinate function, ", "Y_nell_h > MAX_FLOAT: ", Y);

                return Y;
        }


        //Nicolosi Globular
        public static double X_nicol(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double B = PI / (2.0 * lonr / RO) - 2.0 * lonr / RO / PI;
                        final double C = 2.0 * lat / RO / PI;
                        final double D = sin(lat / RO) - C;

                        //Throw exception
                        if (abs(D) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_nicol coordinate function, ", "1 / D, D = 0:", D);

                        final double E = (1 - C * C) / D;

                        //Throw exception
                        if (abs(E) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_nicol coordinate function, ", "1 / E, E = 0:", E);

                        final double F = 1 + B * B / (E * E);
                        final double G = cos(lat / RO);

                        //Throw exception
                        if (abs(F) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_nicol coordinate function, ", "1 / F, F = 0:", F);

                        final double M = (B * sin(lat / RO) / E - B / 2) / F;
                        double N = M * M + G * G / F;

                        //Throw exception
                        if (N < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(N) in X_nicol coordinate function, ", "N < 0: ", N);

                        //Correct N
                        if (N < 0.0) N = 0.0;
                
                        X = R * PI / 2.0 * (M + signum(lonr) * sqrt(N)) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_nicol coordinate function, ", "X_nicol > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_nicol(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double B = PI / (2.0 * lonr / RO) - 2.0 * lonr / RO / PI;

                        //Throw exception
                        if (abs(B) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_nicol coordinate function, ", "1 / B, B = 0:", B);

                        final double C = 2.0 * lat / RO / PI;
                        final double D = sin(lat / RO) - C;

                        //Throw exception
                        if (abs(D) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_nicol coordinate function, ", "1 / D, D = 0:", D);

                        final double E = (1.0 - C * C) / D;
                        final double F = 1.0 + E * E / (B * B);

                        //Throw exception
                        if (abs(F) < MIN_FLOAT)
                                throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_nicol coordinate function, ", "1 / F, F = 0:", F);

                        final double G = sin(lat / RO);
                        final double N = (E * E * G / (B * B) + E / 2.0) / F;
                        double P = N * N - (E * E * G * G / (B * B) + E * G - 1.0) / F;

                        //Throw exception
                        if (P < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(P) in Y_nicol coordinate function, ", "P < 0: ", P);

                        //Correct P
                        if (P < 0.0) P = 0.0;

                        Y = R * PI / 2.0 * (N + signum(-lat) * sqrt(P)) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_nicol coordinate function, ", "Y_nicol > MAX_FLOAT: ", Y);

                return Y;

        }


        //Ortelius
        public static double X_ortel(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if (abs(lonr) < 90)
                {
                        //Call Apian projection
                        X = X_api(R, lat1, lat2, lat, lon, lon0, dx, dy, c);
                }

                else
                {
                        X = R * signum(lonr) * (sqrt(PI * PI / 4.0 - lat * lat / (RO * RO)) + abs(lonr / RO) - PI / 2) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_ortel coordinate function, ", "X_ortel > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_ortel(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_ortho coordinate function, ", "Y_ortho > MAX_FLOAT: ", Y);

                return Y;
        }


        //Orthographic
        public static double X_ortho(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * cos(lat / RO) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_ortho coordinate function, ", "X_ortho > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_ortho(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double Y = -R * cos(lat / RO) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_ortho coordinate function, ", "Y_ortho > MAX_FLOAT: ", Y);

                return Y;
        }


        //Parabolic
        public static double X_parab(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = sqrt(3.0 / PI) * R * lonr / RO * (2.0 * cos(2.0 * lat / 3.0 / RO) - 1) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_parab coordinate function, ", "X_parab > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_parab(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = sqrt(3.0 * PI) * R * sin(lat / 3.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_parab coordinate function, ", "Y_parab > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        
        //Peirce Quincuncial
        public static double X_peiq(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
	
               	final double a = cos(lat / RO) * ( cos(lonr / RO) - sin(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in X_peiq coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = cos(lat / RO) * ( sin(lonr / RO) + cos(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in X_peiq coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                final double  v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in X_peiq coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in X_peiq coordinate function, ", "abs(n) > 1: ", n);

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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_peiq coordinate function, ", "X_peiq > MAX_FLOAT: ", X);

                return X;
        }
    
        public static double Y_peiq(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                
                final double a = cos(lat / RO) * (cos(lonr / RO) - sin(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(a) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(a) in Y_peiq coordinate function, ", "abs(a) > 1: ", a);

                final double A = acos(a);
                final double b = cos(lat / RO) * (sin(lonr / RO) + cos(lonr / RO)) / sqrt(2.0);

                //Throw exception
                if (abs(b) > 1.0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate acos(b) in Y_peiq coordinate function, ", "abs(b) > 1: ", b);

                final double B = acos(b);
                final double  v = 0.5 * (A - B);
                double m = sqrt(2) * sin(v);

                //Throw exception
                if (m > 1.0 + ARGUMENT_ROUND_ERROR || m < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(m) in Y_peiq coordinate function, ", "abs(m) > 1: ", m);

                //Correct m
                if (m > 1.0) m = 1.0;
                else if (m < -1.0) m = -1.0;

                final double  M = asin(m);
                final double u = 0.5 * (A + B);
                double n = sqrt(2) * cos(u);

                //Throw exception
                if (n > 1.0 + ARGUMENT_ROUND_ERROR || n < -1.0 - ARGUMENT_ROUND_ERROR)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate asin(n) in Y_peiq coordinate function, ", "abs(n) > 1: ", n);

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
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_peiq coordinate function, ", "Y_peiq > MAX_FLOAT: ", Y);

                return Y;
        }


        //Perspective Azimuthal
        public static double X_pers(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * cos(lat / RO) * sqrt(5) / (sqrt(5) + 1 - sin(lat / RO)) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_pers coordinate function, ", "X_pers > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_pers(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double Y = -R * cos(lat / RO) * sqrt(5) / (sqrt(5) + 1 - sin(lat / RO)) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_pers coordinate function, ", "Y_pers > MAX_FLOAT: ", Y);

                return Y;
        }


        //Far-side Perspective Azimuthal
        public static double X_persf(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * ((1 + c) + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_persf coordinate function, ", "1 / A, A = 0:", A);

                final double X = R * (2.0 + c) * R * cos(lat / RO) / A * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_persf coordinate function, ", "X_persf > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_persf(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * ((1 + c) + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_persf coordinate function, ", "1 / A, A = 0:", A);

                final double Y = -R * (2.0 + c) * R * cos(lat / RO) / A * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_persf coordinate function, ", "Y_persf > MAX_FLOAT: ", Y);

                return Y;
        }


        //Near-side Perspective Azimuthal
        public static double X_persn(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * ((1 + c) + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_persn coordinate function, ", "1 / A, A = 0:", A);

                final double X = R * c * R * cos(lat / RO) / A * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_persn coordinate function, ", "X_persn > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_persn(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * ((1 + c) + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_persn coordinate function, ", "1 / A, A = 0:", A);

                final double Y = -R * c * R * cos(lat / RO) / A * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_persn coordinate function, ", "Y_persn > MAX_FLOAT: ", Y);

                return Y;
        }


        //Hassler Polyconic, American
        public static double X_poly(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0;

                if ( abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = R * lonr / RO + dx;
                }

                else
                {
                        final double E = lonr / RO * sin(lat / RO);

                        X = R * 1 / tan(lat / RO) * sin(E) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_poly coordinate function, ", "X_poly > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_poly(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0;

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = -R * lat1 / RO + dy;
                }

                else
                {
                        final double E = lonr / RO * sin(lat / RO);

                        Y = R * (lat/RO - lat1/RO + 1.0 / tan(lat/RO) * (1 - cos(E))) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_poly coordinate function, ", "Y_poly > MAX_FLOAT: ", Y);

                return Y;
        }


        //Putnins P1
        public static double X_putp1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 1 - 3.0 * pow((lat / (RO * PI)), 2);

                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in X_putp1 coordinate function, ", "A < 0: ", A);


                final double X = 0.94745 * R * lonr / RO * sqrt(A) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_putp1 coordinate function, ", "X_putp1 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 0.94745 * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_putp1 coordinate function, ", "Y_putp1 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Putnins P2
        public static double X_putp2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double latr = lat / RO;
                final double theta0 = ( 0.615709 * latr + 0.00909953 * latr * latr * latr + 0.0046292 * latr * latr * latr * latr * latr) * RO;
                final double theta = NewtonRaphson(Projections::FTheta_putp2, Projections::FThetaDer_putp2, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 1.97949 * R * lonr / RO * (cos(theta / RO) - 0.5) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_putp2 coordinate function, ", "X_putp2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double latr = lat / RO;
                final double theta0 = (0.615709 * latr + 0.00909953 * latr * latr * latr + 0.0046292 * latr * latr * latr * latr * latr) * RO;

                final double theta = NewtonRaphson(Projections::FTheta_putp2, Projections::FThetaDer_putp2, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 1.71848 * R * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_putp2 coordinate function, ", "Y_putp2 > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FTheta_putp2(final double lat, final double theta)
        {
                return theta / RO + sin(theta/RO) * (cos(theta / RO) - 1) - (4.0 * PI - 3.0 * sqrt(3)) / 12.0 * sin(lat / RO);
        }


        public static double FThetaDer_putp2(final double lat, final double theta)
        {
                return (1 + cos(theta / RO) * (cos(theta / RO) - 1) - sin(theta / RO) * sin(theta / RO)) / RO;
        }

        
        //Putnins P3
        public static double X_putp3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = sqrt(2.0 / PI) * R * lonr / RO * (1 - 4.0 * pow((lat / (RO * PI)), 2)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_putp3 coordinate function, ", "X_putp3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = sqrt(2.0 / PI) * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_putp3 coordinate function, ", "Y_putp3 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Putnins P3P
        public static double X_putp3p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = sqrt(2.0 / PI) * R * lonr / RO * (1 - 2.0 * pow((lat / (RO * PI)), 2)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_putp3p coordinate function, ", "X_putp3p > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp3p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = sqrt(2.0 / PI) * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_putp3p coordinate function, ", "Y_putp3p > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Putnins P4P
        public static double X_putp4p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = sin(lat / RO);
                final double B = asin(5 * sqrt(2) / 8 * A);
                final double C = cos(B / 3);

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_putp4p coordinate function, ", "1 / C, C = 0:", C);

                final double X = 2.0 * sqrt(0.6 / PI) * R * lonr / RO * cos(B) / C + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_putp4p coordinate function, ", "X_putp4p > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp4p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * R * sqrt(1.2 * PI) * sin(asin(5 * sqrt(2) / 8 * sin(lat / RO)) / 3) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_putp4p coordinate function, ", "Y_putp4p > MAX_FLOAT: ", Y);

                return Y;
        }


        //Putnins P5
        public static double X_putp5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 1.01346 * R * lonr / RO * (2.0 - sqrt(1 + 12 * pow((lat / (RO * PI)), 2))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_putp5 coordinate function, ", "X_putp5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 1.01346 * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_putp5 coordinate function, ", "Y_putp5 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Putnins P5P
        public static double X_putp5p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 1.01346 * R * lonr / RO * (1.5 - 0.5 * sqrt(1 + 12 * pow((lat / (RO * PI)), 2))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_putp5p coordinate function, ", "X_putp5p > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp5p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 1.01346 * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_putp5p coordinate function, ", "Y_putp5p > MAX_FLOAT: ", Y);

                return Y;
        }


        //Putnins P6
        public static double X_putp6(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_putp6, Projections::FThetaDer_putp6, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 1.01346 * R * lonr / RO * (2.0 - sqrt( 1 + theta / RO * theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_putp6 coordinate function, ", "X_putp6 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp6(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_putp6, Projections::FThetaDer_putp6, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 0.9191 * R * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_putp6 coordinate function, ", "Y_putp6 > MAX_FLOAT: ", Y);

                return Y;
        }

        
        public static double FTheta_putp6(final double lat, final double theta)
        {
                final double p = 2.14714 * sin(lat / RO);
                final double r = sqrt(1 + theta / RO * theta / RO) ;

                return (4.0 - r) * theta / RO - log(theta / RO + r) - p;
        }

        
        public static double FThetaDer_putp6(final double lat, final double theta)
        {
                return (4.0 - 2.0 * sqrt(1 + theta / RO * theta / RO)) / RO;
        }


        //Putnins P6P
        public static double X_putp6p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_putp6p, Projections::FThetaDer_putp6p, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 0.44329 * R * lonr / RO * (3.0 - sqrt(1 + theta / RO * theta / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_putp6p coordinate function, ", "X_putp6p > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_putp6p(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                final double theta0 = lat;
                final double theta = NewtonRaphson(Projections::FTheta_putp6p, Projections::FThetaDer_putp6p, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 0.80404 * R * theta / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_putp6p coordinate function, ", "Y_putp6p > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_putp6p(final double lat, final double theta)
        {
                final double A = sqrt(1 + theta / RO * theta / RO);

                return (6.0 - A) * theta / RO - log(theta / RO + A) - 5.61125 * sin(lat / RO);
        }


        public static double FThetaDer_putp6p(final double lat, final double theta)
        {
                return (6.0 - 2.0 * sqrt(1 + theta / RO * theta / RO)) / RO;
        }
        
        
        //Quartic Authalic
        public static double X_qua_aut(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                //Throw exception
                final double X = R * lonr / RO * cos(lat / RO) / cos(lat / 2.0 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_qua_aut coordinate function, ", "X_qua_aut > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_qua_aut(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 2.0 * R * sin(lat / 2.0 / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_qua_aut coordinate function, ", "Y_qua_aut > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Rectangular Polyconic
        public static double X_rpoly(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double X = 0, A = 0;

                if (abs(lat1) < MAX_ANGULAR_DIFF)
                {
                        A = 0.5 * lonr / RO;
                }

                else
                {
                        A = tan(0.5 * lonr / RO * sin(lat1 / RO)) / sin(lat1 / RO);
                }

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        X = 2.0 * R * A + dx;
                }

                else
                {
                        final double E = 2.0 * atan(A * sin(lat / RO));

                        //Throw exception
                        if (abs(abs(lat) - MAX_LAT) < MAX_ANGULAR_DIFF)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate tan(lat) in X_rpoly coordinate function, ", "lat = +-90: ", lat);

                        X = R / tan(lat / RO) * sin(E) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_rpoly coordinate function, ", "X_rpoly > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_rpoly(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);
                double Y = 0, A = 0;

                if (abs(lat1) < MAX_ANGULAR_DIFF)
                {
                        A = 0.5 * lonr / RO;
                }

                else
                {
                        A = tan(0.5 * lonr / RO * sin(lat1 / RO)) / sin(lat1 / RO);
                }

                if (abs(lat) < MAX_ANGULAR_DIFF)
                {
                        Y = -R * lat1 / RO + dy;
                }

                else
                {
                        final double E = 2.0 * atan(A * sin(lat / RO));

                        //Throw exception
                        if (abs(abs(lat) - MAX_LAT) < MAX_ANGULAR_DIFF)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate tan(lat) in Y_rpoly coordinate function, ", "lat = +-90: ", lat);

                        Y = R * (lat / RO - lat1 / RO + 1.0 / tan(lat / RO) * (1 - cos(E))) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_rpoly coordinate function, ", "Y_rpoly > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Sinusoidal
        public static double X_sinu(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = R * lonr * cos(lat / RO) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_sinu coordinate function, ", "X_sinu > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_sinu(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_sinu coordinate function, ", "Y_psinu > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Solovyev Azimuthal
        public static double X_solo(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 4;

                //Throw exception
                if (abs(abs(A) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in X_solo coordinate function, ", "lat = +-90: ", lat);

                final double X = 4.0 * R * tan(A / RO) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_solo coordinate function, ", "X_solo > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_solo(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 4;

                //Throw exception
                if (abs(abs(A) - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in Y_solo coordinate function, ", "lat = +-90: ", lat);

                final double Y = -4.0 * R * tan(A / RO) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_solo coordinate function, ", "Y_solo > MAX_FLOAT: ", Y);

                return Y;
        }


        //Stereographic
        public static double X_stere(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2;

                //Throw exception
                if (abs(A - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in X_stere coordinate function, ", "lat = +-90: ", lat);

                final double X = 2.0 * R * tan(A / RO) * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_stere coordinate function, ", "X_stere > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_stere(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = (90 - lat) / 2;

                //Throw exception
                if (abs(A - 90) < MAX_ANGULAR_DIFF)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate tan(lat) in Y_stere coordinate function, ", "lat = +-90: ", lat);

                final double Y = -2.0 * R * tan(A / RO) * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_stere coordinate function, ", "Y_stere > MAX_FLOAT: ", Y);

                return Y;
        }


        //Twilight General Vertical Perspective
        public static double X_twi(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * (1.4 + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_twi coordinate function, ", "1 / A, A = 0:", A);

                final double X = R * 2.4 * R * cos(lat / RO) / A * sin(lonr / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_twi coordinate function, ", "X_twi > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_twi(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = R * (1.4 + sin(lat / RO));

                //Throw exception
                if (abs(A) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_twi coordinate function, ", "1 / A, A = 0:", A);

                final double Y = -R * 2.4 * R * cos(lat / RO) / A * cos(lonr / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_twi coordinate function, ", "Y_twi > MAX_FLOAT: ", Y);

                return Y;
        }


        //Urmaev V
        public static double X_urm5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = asin(0.8 *sin(lat / RO));

                final double X = 2.0 * pow(3, 0.25) / 3.0 * R * lonr / RO * cos(A) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_urm5 coordinate function, ", "X_urm5 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_urm5(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {

                final double A = asin(0.8 *sin(lat / RO));
                final double Y = R * A * (1 + 0.414524 / 3.0 * pow((A / RO), 2)) / (0.8 * 2.0 * pow(3, 0.25) / 3) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_urm5 coordinate function, ", "Y_urm5 > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Van der Grinten I
        public static double X_vandg(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = A * A;
                        final double E = B + C - 1;

                        //Throw exception
                        if (abs(E) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_vandg coordinate function, ", "1 / E, E = 0:", E);

                        final double G = C / E;
                        final double P = G * (2.0 / B - 1);
                        final double Q = D + G;
                        final double S = P * P + D;
                        final double TT = G - P * P;
                        double U = A * A * TT * TT - S * (G * G - P * P);

                        //Throw exception
                        if (U < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in X_vandg coordinate function, ", "U < 0: ", U);

                        //Correct U
                        if (U < 0.0) U = 0.0;

                        //Throw exception
                        if (abs(S) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_vandg coordinate function, ", "1 / S, S = 0:", S);

                        X = R * PI * signum(lonr) * (A * TT + sqrt(U)) / S + dx;
                }


                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_vandg coordinate function, ", "X_vandg > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_vandg(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = A * A;
                        final double E = B + C - 1;

                        //Throw exception
                        if (abs(E) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate Y_vandg coordinate function, ", "1 / E, E = 0:", E);

                        final double G = C / E;
                        final double P = G * (2.0 / B - 1);
                        final double Q = D + G;
                        final double S = P * P + D;
                        double V = (A * A + 1) * S - Q * Q;

                        //Throw exception
                        if (V < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in Y_vandg coordinate function, ", "V < 0: ", V);

                        //Correct V
                        if (V < 0.0) V = 0.0;
                        
                        //Throw exception
                        if (abs(S) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate Y_vandg coordinate function, ", "1 / S, S = 0:", S);

                        Y = R * PI * signum(lat) * (P * Q - A * sqrt(V)) / S + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_vandg coordinate function, ", "Y_vandg > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Van der Grinten II
        public static double X_vandg2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = 1 + A * A * B * B;

                        //Throw exception
                        if (abs(D) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_vandg2 coordinate function, ", "1 / D, D = 0:", D);

                        final double X1 = (C * sqrt(1 + A * A) - A * C * C) / D;

                        X = R * PI * signum(lonr) * X1 + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_vandg2 coordinate function, ", "X_vandg2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_vandg2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double A = 0.5 * abs(180 / lonr - lonr / 180);
                        final double D = 1 + A * A * B * B;

                        //Throw exception
                        if (abs(D) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate Y_vandg2 coordinate function, ", "1 / D, D = 0:", D);

                        final double X1 = (C * sqrt(1 + A * A) - A * C * C) / D;
                        final double E = 1 - X1 * X1 - 2.0 * A * X1;

                        //Throw exception
                        if (E < 0)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(E) in Y_vandg2 coordinate function, ", "E < 0: ", E);

                        Y = R * PI * signum(lat) * sqrt(E) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_vandg2 coordinate function, ", "Y_vandg2 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Van der Grinten III
        public static double X_vandg3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double A = 0.5 * abs(180.0 / lonr - lonr / 180.0);
                        final double Y1 = B / (1 + C);
                        final double D = 1.0 + A * A - Y1 * Y1;

                        //Throw exception
                        if (abs(D) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_vandg3 coordinate function, ", "1 / D, D = 0:", D);

                        X = R * PI * signum(lonr) * (sqrt(D) - A) + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_vandg3 coordinate function, ", "X_vandg3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_vandg3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_vandg3 coordinate function, ", "Y_vandg3 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Van der Grinten IV
        public static double X_vandg4(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double C = 0.5 * (B * (8.0 - B * (2.0 + B * B)) - 5) / (B * B * (B - 1));
                        final double C1 = 90 / lonr + lonr / 90;
                        double C2 = C1 * C1 - 4;

                        //Throw exception
                        if (C2 < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in X_vandg4 coordinate function, ", "C2 < 0: ", C2);

                        //Correct C2
                        if (C2 < 0.0) C2 = 0.0;
                        
                        final double D = signum(abs(lonr) - 90) * sqrt(C2);
                        final double F1 = (B + C) * (B + C);
                        final double F2 = (B + 3.0 * C) * (B + 3.0 * C);
                        double F = F1 * (B * B + C * C * D * D - 1) + (1 - B * B) * (B * B * (F2 + 4.0 * C * C) + 12 * B * C * C * C + 4.0 * C * C * C * C);

                        //Throw exception
                        if (F < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in X_vandg4 coordinate function, ", "F < 0: ", F);

                        //Correct C2
                        if (F < 0.0) F = 0.0;

                        final double G = 4.0 * F1 + D * D;

                        //Throw exception
                        if (abs(G) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_vandg4 coordinate function, ", "1 / G, G = 0:", G);

                        final double X1 = (D * (F1 + C * C - 1) + 2.0 * sqrt(F)) / G;

                        X = R * PI * 0.5 * signum(lonr) * X1 + dx;
                }

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_vandg4 coordinate function, ", "X_vandg4 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_vandg4(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        final double C = 0.5 * (B * (8.0 - B * (2.0 + B * B)) - 5) / (B * B * (B - 1));
                        final double C1 = 90 / lonr + lonr / 90;
                        double C2 = C1 * C1 - 4;

                        //Throw exception
                        if (C2 < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in X_vandg4 coordinate function, ", "C2 < 0: ", C2);

                        //Correct C2
                        if (C2 < 0.0) C2 = 0.0;

                        final double D = signum(abs(lonr) - 90) * sqrt(C2);
                        final double F1 = (B + C) * (B + C);
                        final double F2 = (B + 3.0 * C) * (B + 3.0 * C);
                        double F = F1 * (B * B + C * C * D * D - 1) + (1 - B * B) * (B * B * (F2 + 4.0 * C * C) + 12 * B * C * C * C + 4.0 * C * C * C * C);

                        //Throw exception
                        if (F < -ARGUMENT_ROUND_ERROR)
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(m) in Y_vandg4 coordinate function, ", "F < 0: ", F);

                        //Correct C2
                        if (F < 0.0) F = 0.0;

                        final double G = 4.0 * F1 + D * D;

                        //Throw exception
                        if (abs(G) < MIN_FLOAT)
                                throw new MathZeroDevisionException("MathZeroDevisionException: can not evaluate X_vandg4 coordinate function, ", "1 / G, G = 0:", G);

                        final double X1 = (D * (F1 + C * C - 1) + 2.0 * sqrt(F)) / G;
                        final double H = 1 + D * abs(X1) - X1 * X1;

                        //Throw exception
                        if (H < 0)
                                throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate sqrt(H) in Y_vandg4 coordinate function, ", "H < 0: ", H);


                        Y = R * PI * 0.5 * signum(lat) * sqrt(H) + dy;
                }

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_vandg4 coordinate function, ", "Y_vandg4 > MAX_FLOAT: ", Y);

                return Y;
        }
        
        
        //Wagner I
        public static double X_wag1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double M = 2.0 * sqrt(sqrt(3)) / 3.0;
                final double N = 0.5 * sqrt(3);
                final double A = N * sin(lat / RO);

                if (abs(A) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(A) in X_wag1 coordinate function, ", "abs(A) > 1: ", A);

                final double theta = asin(A) * RO;

                final double X = R * M * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate X_wag1 coordinate function, ", "X_wag1 > MAX_FLOAT: ", X);

                return X;
        }

        
        public static double Y_wag1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double M = 2.0 * sqrt(sqrt(3)) / 3.0;
                final double N = 0.5 * sqrt(3);
                final double A = N * sin(lat / RO);

                if (abs(A) > 1.0)
                        throw new MathInvalidArgumentException("MathInvalidArgumentException: can not evaluate asin(A) in Y_wag1 coordinate function, ", "abs(A) > 1: ", A);

                final double theta = asin(A) * RO;

                final double Y = R * 3.0 * theta / RO * M * N /2.0 + dy;		//Error in Evenden G, I: Cartographic Projection Procedures, page 7

                //Throw exception
                if (abs(Y) > MAX_FLOAT)
                        throw new MathOverflowException("MathOverflowException: can not evaluate Y_wag1 coordinate function, ", "Y_wag1 > MAX_FLOAT: ", Y);

                return Y;
        }
        

        //Wagner II
        public static double X_wag2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 0.92483 * R * lonr / RO * cos(asin(0.88022 * sin(0.8855 * lat / RO))) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wag2 coordinate function, ", "X_wag2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wag2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 1.38725 * R * asin(0.88022 * sin(0.8855 * lat / RO)) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wag2 coordinate function, ", "Y_wag2 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Wagner III
        public static double X_wag3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = lat1 / RO;
                final double B = cos(2.0 * A / 3);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_wag3 coordinate function, ", "1 / A, A = 0:", A);


                final double X = R * lonr / RO * (cos(A) / B) * cos(2.0 * lat / 3.0 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wag3 coordinate function, ", "X_wag3 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wag3(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wag3 coordinate function, ", "Y_wag3 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Wagner IV
        public static double X_wag4(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = 0.5 * lat;
                final double theta = NewtonRaphson(Projections::FTheta_wag4, Projections::FThetaDer_wag4, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 0.8631 * R * lonr / RO * cos(theta / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wag4 coordinate function, ", "X_wag4 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wag4(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = 0.5 * lat;
                final double theta = NewtonRaphson(Projections::FTheta_wag4, Projections::FThetaDer_wag4, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = 1.5654 * R * sin(theta / RO) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wag4 coordinate function, ", "Y_wag4 > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double FTheta_wag4(final double lat, final double theta)
        {
                return 2.0 * theta / RO + sin(2.0 * theta / RO) - (4.0 * PI + 3.0 * sqrt(3)) / 6 * sin(lat / RO);
        }


        public static double FThetaDer_wag4(final double lat, final double theta)
        {
                return ( 2.0 + 2.0 * cos(2.0 * theta / RO)) / RO;
        }


        //Wagner VI
        public static double X_wag6(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 1 - 3.0 * pow((lat / (PI * RO)), 2);

                //Throw exception
                if (A < 0)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in X_wag6 coordinate function, ", "A < 0: ", A);

                final double X = 1.89490 * R * (-0.5 + sqrt(A)) * lonr / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wag6 coordinate function, ", "X_wag6 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wag6(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = 0.94745 * R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wag6 coordinate function, ", "Y_wag6 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Wagner VII
        public static double X_wag7(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = pow((0.90631 * sin(lat / RO)), 2);

                //Throw exception
                if (A > 1)
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: can not evaluate sqrt(A) in X_wag7 coordinate function, ", "A < 0: ", A);

                final double B = sqrt(1 - A);
                final double C = 1 + B * cos(lonr / 3.0 / RO);

                //Throw exception
                if (abs(C) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_wag7 coordinate function, ", "1 / C, C = 0:", C);

                final double D = 2.0 / C;

                final double X = 2.66723 * R * B * sqrt(D) * sin(lonr / 3.0 / RO) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wag7 coordinate function, ", "X_wag7 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wag7(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = 0.90631 * sin(lat / RO);
                final double B = 1 + sqrt(1 - pow((A), 2)) * cos(lonr / 3.0 / RO);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate Y_wag7 coordinate function, ", "1 / B, B = 0:", B);

                final double Y = 1.24104 * R * A * sqrt(2.0 / B) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wag7 coordinate function, ", "Y_wag7 > MAX_FLOAT: ", Y);

                return Y;
        }

        
        //Werner-Staab
        public static double X_wer(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wer coordinate function, ", "X_wer > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wer(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
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
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wer coordinate function, ", "Y_wer > MAX_FLOAT: ", Y);

                return Y;
        }


        //Werenskiold I
        public static double X_weren(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double A = asin(5 * sqrt(2) / 8 * sin(lat / RO));
                final double B = cos(A / 3);

                //Throw exception
                if (abs(B) < MIN_FLOAT)
                        throw new  MathZeroDevisionException ("MathDivisonByZeroException: can not evaluate X_weren coordinate function, ", "1 / B, B = 0:", B);

                final double X = R * lonr / RO * cos(A) / B + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_weren coordinate function, ", "X_weren > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_weren(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double A = asin(5 * sqrt(2) / 8 * sin(lat / RO));
                final double Y = 2.0 * PI * sqrt(2) * R * sin(A / 3) + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_weren coordinate function, ", "Y_weren > MAX_FLOAT: ", Y);

                return Y;
        }


        //Winkel I
        public static double X_wink1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double X = 0.5 * R * lonr * (cos(lat1 / RO) + cos(lat / RO)) / RO + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wink1 coordinate function, ", "X_wink1 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wink1(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double Y = R * lat / RO + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wink1 coordinate function, ", "Y_wink1 > MAX_FLOAT: ", Y);

                return Y;
        }


        //Winkel II
        public static double X_wink2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double lonr = CartTransformation.redLon0(lon, lon0);

                final double theta0 = 0.9 * lat;
                final double theta = NewtonRaphson(Projections::FTheta_wink2, Projections::FThetaDer_wink2, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double X = 0.5 * R * lonr / RO * (cos(theta / RO) + cos(lat1 / RO)) + dx;

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wink2 coordinate function, ", "X_wink2 > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wink2(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                final double theta0 = 0.9 * lat;
                final double theta = NewtonRaphson(Projections::FTheta_wink2, Projections::FThetaDer_wink2, lat, theta0, MAX_NR_ITERATIONS, MAX_NR_ERROR);

                final double Y = PI * R * (sin(theta / RO) + 2.0 * lat / PI / RO) / 4.0 + dy;

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wink2 coordinate function, ", "Y_wink2 > MAX_FLOAT: ", Y);

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

        
        //Winkel Tripel
        public static double X_wintri(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                //Aitoff projection
                final double X1 = X_aitoff(R, lat1, lat2, lat, lon, lon0, dx, dy, c);

                //Equidistant conic
                final double X2 = X_eqc(R, lat1, lat2, lat, lon, lon0, dx, dy, c);

                //Average of both projections
                final double X = 0.5 * (X1 + X2);

                //Throw exception
                if (abs(X) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate X_wintri coordinate function, ", "X_wintri > MAX_FLOAT: ", X);

                return X;
        }


        public static double Y_wintri(final double R, final double lat1, final double lat2, final double lat, final double lon, final double lon0, final double dx, final double dy, final double c)
        {
                //Aitoff projection
                final double Y1 = Y_aitoff(R, lat1, lat2, lat, lon, lon0, dx, dy, c);

                //Equidistant conic
                final double Y2 = Y_eqc(R, lat1, lat2, lat, lon, lon0, dx, dy, c);

                //Average of both projections
                final double Y = 0.5 * (Y1 + Y2);

                //Throw exception
                if (abs(Y) > MAX_FLOAT )
                        throw new MathOverflowException ("MathOverflowException: can not evaluate Y_wintri coordinate function, ", "Y_wintri > MAX_FLOAT: ", Y);

                return Y;
        }


        public static double NewtonRaphson(IThetaFunction ftheta, IThetaFunction fthetader, final double lat, final double theta0, final int MAX_ITERATIONS, double MAX_DIFF )
        {
                //Solve theta = f(theta, lat) by the Newton-Raphson method
                //Used in several pseudocylindrical projections
                int iterations = 0;
                double theta = theta0;

                //Apply Newton-Raphson method
                do {
                        //Compute F(theta) and F'(theta)
                        final double ft = ftheta.f(lat, theta);
                        final double ft_der = fthetader.f(lat, theta);

                        //Newton-Raphson step
                        double theta_n = theta;
                        if (abs(ft_der) > MIN_FLOAT) {
                                theta_n = theta - ft / ft_der;
                        }

                        //Test the terminal condition
                        if (abs(theta_n - theta) < MAX_DIFF) {
                                break;
                        }

                        //Assignum new theta
                        theta = theta_n;

                } while (++iterations <= MAX_ITERATIONS);

                return theta;
        }
}