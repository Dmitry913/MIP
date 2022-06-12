import javax.swing.JFrame;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Double, InformationAboutJointPoint> jointPoints = new HashMap<>();

        // test1 - сглаженный вариант
//        jointPoints.put(0.0, new InformationAboutJointPoint(0.0, 0.0, 0.0, 0.0));
//        jointPoints.put(1.0, new InformationAboutJointPoint(0.0, 1.0, 1.0, 1.0));
//        jointPoints.put(2.0, new InformationAboutJointPoint(1.0, 1.0, 1.0, -1.0));
//        jointPoints.put(3.0, new InformationAboutJointPoint(1.0, 0.0, 0.0, 0.0));

        // test2 - вариант из книжки
        jointPoints.put(0.0, new InformationAboutJointPoint(0.0, 0.0, 0.0, 0.0));
        jointPoints.put(1.0, new InformationAboutJointPoint(0.0, 1.0, 1.0, 0.0));
        jointPoints.put(2.0, new InformationAboutJointPoint(1.0, 1.0, 0.0, -1.0));
        jointPoints.put(3.0, new InformationAboutJointPoint(1.0, 0.0, 0.0, 0.0));

        Interpolation interpolation = new Interpolation();
        interpolation.setJointPoints(jointPoints);
        interpolation.interpolate();
        XYSeries series = new XYSeries("Interpolation", false);
        for(int i = 0; i < interpolation.getResultCoordinatesX().size(); i++){
            series.add(interpolation.getResultCoordinatesX().get(i), interpolation.getResultCoordinatesY().get(i));
        }
        interpolation.showResult(List.of(series), "Graph interpolation");

        List<XYSeries> listSeries = new ArrayList<>();
        series = new XYSeries("X", false);
        for(int i = 0; i < interpolation.getResultCoordinatesX().size(); i++){
            series.add(interpolation.getTimesStep().get(i), interpolation.getResultCoordinatesX().get(i));
        }
        listSeries.add(series);
        series = new XYSeries("Y", false);
        for(int i = 0; i < interpolation.getResultCoordinatesX().size(); i++){
            series.add(interpolation.getTimesStep().get(i), interpolation.getResultCoordinatesY().get(i));
        }
        listSeries.add(series);
        interpolation.showResult(listSeries, "position");

        listSeries = new ArrayList<>();
        series = new XYSeries("X", false);
        for(int i = 0; i < interpolation.getResultCoordinatesX().size(); i++){
            series.add(interpolation.getTimesStep().get(i), interpolation.getResultVelocitiesX().get(i));
        }
        listSeries.add(series);
        series = new XYSeries("Y", false);
        for(int i = 0; i < interpolation.getResultCoordinatesX().size(); i++){
            series.add(interpolation.getTimesStep().get(i), interpolation.getResultVelocitiesY().get(i));
        }
        listSeries.add(series);
        interpolation.showResult(listSeries, "velocity");
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Interpolation {
    private static final Integer COUNT_SUBSEGMENT = 20;
    private List<Double> times = new ArrayList<>();
    private Map<Double, InformationAboutJointPoint> jointPoints = new HashMap<>();
    private List<Double> timesStep = new ArrayList<>();
    private List<Double> resultCoordinatesX = new ArrayList<>();
    private List<Double> resultVelocitiesX = new ArrayList<>();
    private List<Double> resultVelocitiesY = new ArrayList<>();
    private List<Double> resultCoordinatesY = new ArrayList<>();

    public void interpolate() {
        times.addAll(jointPoints.keySet());
        Collections.sort(times);
        for (int j = 0; j < times.size() - 1; j++) {
            resultCoordinatesX.addAll(fillXCoordinatesCorrect(j, COUNT_SUBSEGMENT).subList(0, COUNT_SUBSEGMENT));
            resultCoordinatesY.addAll(fillYCoordinatesCorrect(j, COUNT_SUBSEGMENT).subList(0, COUNT_SUBSEGMENT));
        }
        timesStep.add(times.get(times.size() - 1));
        resultCoordinatesX.add(jointPoints.get(times.get(times.size() - 1)).getCoordinateX());
        resultVelocitiesX.add(jointPoints.get(times.get(times.size() - 1)).getVelocityX());
        resultCoordinatesY.add(jointPoints.get(times.get(times.size() - 1)).getCoordinateY());
        resultVelocitiesY.add(jointPoints.get(times.get(times.size() - 1)).getVelocityY());
    }

    public void showResult(List<XYSeries> listSeries, String graphName) {
        XYSeriesCollection xyDataset = new XYSeriesCollection();
        for (XYSeries series : listSeries) {
            xyDataset.addSeries(series);
        }
        JFreeChart chart = ChartFactory
                .createXYLineChart(graphName, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("Chart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(800,800);
        frame.show();
    }

    public List<Double> fillXCoordinatesCorrect(int index, Integer countSubsegment) {
        List<Double> coordinates = new ArrayList<>();
        InformationAboutJointPoint currentInformation = jointPoints.get(times.get(index));
        InformationAboutJointPoint nextInformation = jointPoints.get(times.get(index + 1));
        Double a0 = 0.0;
        Double a1 = currentInformation.getVelocityX();
        Double a2 = calculateA2(a0, a1, nextInformation.getCoordinateX() - currentInformation.getCoordinateX(), nextInformation.getVelocityX(),
                times.get(index + 1) - times.get(index));
        Double a3 = calculateA3(a0, a1, nextInformation.getCoordinateX() - currentInformation.getCoordinateX(), nextInformation.getVelocityX(),
                times.get(index + 1) - times.get(index));

        Double start = 0.0;
        Double deltaT = (times.get(index + 1) - times.get(index)) / countSubsegment;
        Double end = times.get(index + 1) - times.get(index);
        while (start <= end) {
            timesStep.add(start + times.get(index));
            coordinates.add(a0 + a1 * start + a2 * Math.pow(start, 2) + a3 * Math.pow(start, 3) + currentInformation.getCoordinateX());
            resultVelocitiesX.add(a1 + 2 * a2 * start + a3* 3 * Math.pow(start, 2));
            start += deltaT;
        }
        return coordinates;
    }

    public List<Double> fillYCoordinatesCorrect(int index, Integer countSubsegment) {
        List<Double> coordinates = new ArrayList<>();
        InformationAboutJointPoint currentInformation = jointPoints.get(times.get(index));
        InformationAboutJointPoint nextInformation = jointPoints.get(times.get(index + 1));
        Double a0 = 0.0;
        Double a1 = currentInformation.getVelocityY();
        Double a2 = calculateA2(a0, a1, nextInformation.getCoordinateY() - currentInformation.getCoordinateY(), nextInformation.getVelocityY(),
                times.get(index + 1) - times.get(index));
        Double a3 = calculateA3(a0, a1, nextInformation.getCoordinateY() - currentInformation.getCoordinateY(), nextInformation.getVelocityY(),
                times.get(index + 1) - times.get(index));

        Double start = 0.0;
        Double deltaT = (times.get(index + 1) - times.get(index)) / countSubsegment;
        Double end = times.get(index + 1) - times.get(index);
        while (start <= end) {
            coordinates.add(a0 + a1 * start + a2 * Math.pow(start, 2) + a3 * Math.pow(start, 3) + currentInformation.getCoordinateY());
            resultVelocitiesY.add(a1 + 2 * a2 * start + a3* 3 * Math.pow(start, 2));
            start += deltaT;
        }
        return coordinates;
    }

    public Double calculateA2(Double coordinateJ, Double velocityJ, Double nextCoordinateJ, Double nextVelocityJ, Double deltaT) {
        return (3 * nextCoordinateJ - 3 * coordinateJ - 2 * velocityJ * deltaT - nextVelocityJ * deltaT) / (deltaT * deltaT);
    }

    public Double calculateA3(Double coordinateJ, Double velocityJ, Double nextCoordinateJ, Double nextVelocityJ, Double deltaT) {
        return (2 * coordinateJ + (velocityJ + nextVelocityJ) * deltaT - 2 * nextCoordinateJ) / Math.pow(deltaT, 3);
    }
}

@AllArgsConstructor
@Getter
@Setter
@Builder
class InformationAboutJointPoint {
    private Double coordinateX;
    private Double coordinateY;
    private Double velocityX;
    private Double velocityY;
}