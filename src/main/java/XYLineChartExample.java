//import javax.swing.JFrame;
//import javax.swing.SwingUtilities;
//import javax.swing.WindowConstants;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author imssbora
// */
//public class XYLineChartExample extends JFrame {
//    private static final long serialVersionUID = 6294689542092367723L;
//
//    Interpolation interpolation;
//
//    public XYLineChartExample(String title, Interpolation interpolation) {
//        super(title);
//
//        // Create dataset
//        XYDataset dataset = createDataset(interpolation);
//
//        // Create chart
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "XY Line Chart Example",
//                "X-Axis",
//                "Y-Axis",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);
//
//        // Create Panel
//        ChartPanel panel = new ChartPanel(chart);
//        setContentPane(panel);
//    }
//
//    private XYDataset createDataset(Interpolation interpolation) {
//        XYSeriesCollection dataset = new XYSeriesCollection();
//
//        XYSeries series = new XYSeries("Y = X + 2", false);
//        for(int i = 0; i < interpolation.getResultCoordinatesY().size(); i++){
//            series.add(interpolation.getResultCoordinatesX().get(i), interpolation.getResultCoordinatesY().get(i));
//        }
//
//        //Add series to dataset
//        dataset.addSeries(series);
//
//        return dataset;
//    }
//
//    public static void main(String[] args) {
//        Map<Double, InformationAboutJointPoint> jointPoints = new HashMap<>();
//
////        jointPoint = {0: [-1, 1, 0, 0],
////                      1: {5, 2, 1, 1},
////                      2: {8, 3, -2, -0.5},
////                      3: {4, 2, -2, -1},
////                      4: {1, 1, 0, 0}}
//
//        // test2
////        jointPoints.put(0.0, new InformationAboutJointPoint(-1.0, 1.0, 0.0, 0.0));
////        jointPoints.put(1.0, new InformationAboutJointPoint(5.0, 2.0, 1.0, 1.0));
////        jointPoints.put(2.0, new InformationAboutJointPoint(8.0, 3.0, -2.0, -0.5));
////        jointPoints.put(3.0, new InformationAboutJointPoint(4.0, 2.0, -2.0, -1.0));
////        jointPoints.put(4.0, new InformationAboutJointPoint(1.0, 1.0, 0.0, 0.0));
//
//        // test1
//        jointPoints.put(0.0, new InformationAboutJointPoint(0.0, 0.0, 0.0, 0.0));
//        jointPoints.put(1.0, new InformationAboutJointPoint(0.0, 1.0, 1.0, 1.0));
//        jointPoints.put(2.0, new InformationAboutJointPoint(1.0, 1.0, 1.0, -1.0));
//        jointPoints.put(3.0, new InformationAboutJointPoint(1.0, 0.0, 0.0, 0.0));
//
//        Interpolation interpolation = new Interpolation();
//        interpolation.setJointPoints(jointPoints);
//        interpolation.interpolate();
////        interpolation.showResult();
//        SwingUtilities.invokeLater(() -> {
//            XYLineChartExample example = new XYLineChartExample("XY Chart Example | BORAJI.COM", interpolation);
//            example.setSize(400, 400);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            example.setVisible(true);
//        });
//    }
//}
//
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//class Interpolation {
//    private static final Integer COUNT_SUBSEGMENT = 20;
//    private List<Double> times = new ArrayList<>();
//    private Map<Double, InformationAboutJointPoint> jointPoints = new HashMap<>();
//    private List<Double> timesStep = new ArrayList<>();
//    private List<Double> resultCoordinatesX = new ArrayList<>();
//    private List<Double> resultCoordinatesY = new ArrayList<>();
//
//    public void interpolate() {
//        times.addAll(jointPoints.keySet());
//        Collections.sort(times);
//        for (int j = 0; j < times.size() - 1; j++) {
//            resultCoordinatesX.addAll(fillXCoordinatesCorrect(j, COUNT_SUBSEGMENT).subList(0, COUNT_SUBSEGMENT));
////            resultCoordinatesX.addAll(fillXCoordinates(j, COUNT_SUBSEGMENT));
////            resultCoordinatesY.addAll(fillYCoordinates(j, COUNT_SUBSEGMENT));
//            resultCoordinatesY.addAll(fillYCoordinatesCorrect(j, COUNT_SUBSEGMENT).subList(0, COUNT_SUBSEGMENT));
//        }
//        resultCoordinatesX.add(jointPoints.get(times.get(times.size() - 1)).getCoordinateX());
//        resultCoordinatesY.add(jointPoints.get(times.get(times.size() - 1)).getCoordinateY());
//    }
//
//    public void showResult() {
//        XYSeries series = new XYSeries("interpolation");
//
//        for(int i = 0; i < resultCoordinatesY.size(); i++){
//            series.add(resultCoordinatesX.get(i), resultCoordinatesY.get(i));
//        }
//
//        XYDataset xyDataset = new XYSeriesCollection(series);
//        JFreeChart chart = ChartFactory
//                .createXYLineChart("interpolation", "x", "y",
//                        xyDataset,
//                        PlotOrientation.VERTICAL,
//                        true, true, true);
//        JFrame frame =
//                new JFrame("MinimalStaticChart");
//        // Помещаем график на фрейм
//        frame.getContentPane()
//                .add(new ChartPanel(chart));
//        frame.setSize(400,300);
//        frame.show();
//    }
//
//    public List<Double> fillXCoordinatesCorrect(int index, Integer countSubsegment) {
//        List<Double> coordinates = new ArrayList<>();
//        InformationAboutJointPoint currentInformation = jointPoints.get(times.get(index));
//        InformationAboutJointPoint nextInformation = jointPoints.get(times.get(index + 1));
//        Double a0 = 0.0;
//        Double a1 = currentInformation.getVelocityX();
//        Double a2 = calculateA2(a0, a1, nextInformation.getCoordinateX() - currentInformation.getCoordinateX(), nextInformation.getVelocityX(),
//                times.get(index + 1) - times.get(index));
//        Double a3 = calculateA3(a0, a1, nextInformation.getCoordinateX() - currentInformation.getCoordinateX(), nextInformation.getVelocityX(),
//                times.get(index + 1) - times.get(index));
//
//        Double start = 0.0;
//        Double deltaT = (times.get(index + 1) - times.get(index)) / countSubsegment;
//        Double end = times.get(index + 1) - times.get(index);
//        while (start <= end) {
//            timesStep.add(start);
//            coordinates.add(a0 + a1 * start + a2 * Math.pow(start, 2) + a3 * Math.pow(start, 3) + currentInformation.getCoordinateX());
//            start += deltaT;
//        }
//        return coordinates;
//    }
//
//    public List<Double> fillYCoordinatesCorrect(int index, Integer countSubsegment) {
//        List<Double> coordinates = new ArrayList<>();
//        InformationAboutJointPoint currentInformation = jointPoints.get(times.get(index));
//        InformationAboutJointPoint nextInformation = jointPoints.get(times.get(index + 1));
//        Double a0 = 0.0;
//        Double a1 = currentInformation.getVelocityY();
//        Double a2 = calculateA2(a0, a1, nextInformation.getCoordinateY() - currentInformation.getCoordinateY(), nextInformation.getVelocityY(),
//                times.get(index + 1) - times.get(index));
//        Double a3 = calculateA3(a0, a1, nextInformation.getCoordinateY() - currentInformation.getCoordinateY(), nextInformation.getVelocityY(),
//                times.get(index + 1) - times.get(index));
//
//        Double start = 0.0;
//        Double deltaT = (times.get(index + 1) - times.get(index)) / countSubsegment;
//        Double end = times.get(index + 1) - times.get(index);
//        while (start <= end) {
//            coordinates.add(a0 + a1 * start + a2 * Math.pow(start, 2) + a3 * Math.pow(start, 3) + currentInformation.getCoordinateY());
//            start += deltaT;
//        }
//        return coordinates;
//    }
//
//    public List<Double> fillXCoordinates(int index, Integer countSubsegment) {
//        List<Double> coordinates = new ArrayList<>();
//        InformationAboutJointPoint currentInformation = jointPoints.get(times.get(index));
//        InformationAboutJointPoint nextInformation = jointPoints.get(times.get(index + 1));
//        Double a0 = currentInformation.getCoordinateX();
//        Double a1 = currentInformation.getVelocityX();
//        Double a2 = calculateA2(a0, a1, nextInformation.getCoordinateX(), nextInformation.getVelocityX(),
//                times.get(index + 1) - times.get(index));
//        Double a3 = calculateA3(a0, a1, nextInformation.getCoordinateX(), nextInformation.getVelocityX(),
//                times.get(index + 1) - times.get(index));
//
//        Double start = times.get(index);
//        Double deltaT = (times.get(index + 1) - times.get(index)) / countSubsegment;
//        Double end = times.get(index + 1);
//        while (start <= end) {
//            timesStep.add(start);
//            coordinates.add(a0 + a1 * start + a2 * Math.pow(start, 2) + a3 * Math.pow(start, 3));
//            start += deltaT;
//        }
//        return coordinates;
//    }
//
//    public List<Double> fillYCoordinates(int index, Integer countSubsegment) {
//        List<Double> coordinates = new ArrayList<>();
//        InformationAboutJointPoint currentInformation = jointPoints.get(times.get(index));
//        InformationAboutJointPoint nextInformation = jointPoints.get(times.get(index + 1));
//        Double a0 = currentInformation.getCoordinateY();
//        Double a1 = currentInformation.getVelocityY();
//        Double a2 = calculateA2(a0, a1, nextInformation.getCoordinateY(), nextInformation.getVelocityY(),
//                times.get(index + 1) - times.get(index));
//        Double a3 = calculateA3(a0, a1, nextInformation.getCoordinateY(), nextInformation.getVelocityY(),
//                times.get(index + 1) - times.get(index));
//
//        Double start = times.get(index);
//        Double deltaT = (times.get(index + 1) - times.get(index)) / countSubsegment;
//        while (start <= times.get(index + 1)) {
//            coordinates.add(a0 + a1 * start + a2 * Math.pow(start, 2) + a3 * Math.pow(start, 3));
//            start += deltaT;
//        }
//        return coordinates;
//    }
//
//    public Double calculateA2(Double coordinateJ, Double velocityJ, Double nextCoordinateJ, Double nextVelocityJ, Double deltaT) {
//        return (3 * nextCoordinateJ - 3 * coordinateJ - 2 * velocityJ * deltaT - nextVelocityJ * deltaT) / (deltaT * deltaT);
//    }
//
//    public Double calculateA3(Double coordinateJ, Double velocityJ, Double nextCoordinateJ, Double nextVelocityJ, Double deltaT) {
//        return (2 * coordinateJ + (velocityJ + nextVelocityJ) * deltaT - 2 * nextCoordinateJ) / Math.pow(deltaT, 3);
//    }
//}
//
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//class InformationAboutJointPoint {
//    private Double coordinateX;
//    private Double coordinateY;
//    private Double velocityX;
//    private Double velocityY;
//}
