package bkp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import sun.net.www.content.image.gif;

/**
 * Evaluate the result based on the expression
 *
 */
public class FormulaUtil {

	private static final double PSI1 = 1.64493407;
	private static final double PSI2 = -2.4041138;
	private static final double PSI3 = 6.49393938;

	public static Map<String, Double> generator(double v, double u, double alpha, double theta, double delta, int n) {

		Map<String, Double> map = new HashMap<String, Double>();

		List<Double> lamda = new ArrayList<>();
		List<Double> mu = new ArrayList<>();

		// Generate two sets of data
		for (int i = 0; i < n; i++) {

			double temp1 = weibull(v, alpha);
			lamda.add(temp1);

			double temp2 = weibull(u, theta);
			mu.add(temp2);
		}

		List<Double> rhoList = new ArrayList<>();

		for (int i = 0; i < lamda.size(); i++) {
			rhoList.add(lamda.get(i) / mu.get(i));
		}

		// L1-l4 was calculated according to all data of the two groups
		double L1 = 0;

		for (Double item : rhoList) {
			L1 += item;
		}

		L1 = L1 / n;

//		double L2 = Math.pow(L1, 2);
//		double L3 = Math.pow(L1, 3);
//		double L4 = Math.pow(L1, 4);

		double L2 = 0;

		for (Double item : rhoList) {
			L2 += Math.pow(item, 2);
		}

		L2 = L2 / n;

		double L3 = 0;

		for (Double item : rhoList) {
			L3 += Math.pow(item, 3);
		}

		L3 = L3 / n;

		double L4 = 0;

		for (Double item : rhoList) {
			L4 += Math.pow(item, 4);
		}

		L4 = L4 / n;

		// Follow the formula to find A1 to A4
		double A1 = L1;

		double A2 = (L2 - Math.pow(L1, 2)) / PSI1;
		double A3 = (L3 - 3 * L1 * (L2 - Math.pow(L1, 2) - Math.pow(L1, 3))) / PSI2;
		double A4 = (L4 - 6 * Math.pow(L1, 2) * (L2 - Math.pow(L1, 2))
				- 4 * L1 * (L3 - 3 * L1 * (L2 - Math.pow(L1, 2)) - Math.pow(L1, 3))
				- 3 * Math.pow((L2 - Math.pow(L1, 2)), 2) - Math.pow(L1, 4)) / PSI3;

		//double A4 = ((L4 - 6*Math.pow(L1,2))*(L2-Math.pow(L1,2))
		//		-4*L1 * (L3 - 3*L1 * L2 - Math.pow(L1,3)) - Math.pow(L1,3))
		//		- Math.pow(L1,4)/PSI3;
		// Let's solve for v1, U1, v2, u2

		double v1 = Math.sqrt(2 / (A2 - Math.sqrt(2 * A4 - Math.pow(A2, 2))));
		double v2 = Math.sqrt(2 / (A2 + Math.sqrt(2 * A4 - Math.pow(A2, 2))));
		double u1 = Math.sqrt(2 / (A2 + Math.sqrt(2 * A4 - Math.pow(A2, 2))));
		double u2 = Math.sqrt(2 / (A2 - Math.sqrt(2 * A4 - Math.pow(A2, 2))));
		double delta1 = Math.abs(1 / Math.pow(v1, 3) - 1 / Math.pow(u1, 3) - A3);
		double delta2 = Math.abs(1 / Math.pow(v2, 3) - 1 / Math.pow(u2, 3) - A3);
		double sigma1 = L1 - PSI1 * (1 / v1 - 1 / u1); //Delta1
		double sigma2 = L1 - PSI1 * (1 / v2 - 1 / u2);  //Delta2
		//DecimalFormat df = new DecimalFormat("0.00");
		//System.out.println(df.format(v2));

		// Put the results into a Map file
		map.put("v1", v1);
		map.put("v2", v2);
		map.put("u1", v1);
		map.put("u2", u2);
		map.put("delta1", delta1);
		map.put("delta2", delta2);
		map.put("sigma1", sigma1);
		map.put("sigma2", sigma2);

		// Returns the result
		return map;
	}

	public static double weibull(double eta, double beta) {
		RandomGenerator rg = new JDKRandomGenerator();
		WeibullDistribution g = new WeibullDistribution(rg, eta, beta,
				WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
		return g.sample();
	}
}
