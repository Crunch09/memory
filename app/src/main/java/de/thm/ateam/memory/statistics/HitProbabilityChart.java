package de.thm.ateam.memory.statistics;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;



public class HitProbabilityChart extends Fragment{
  
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {

      return getView(getActivity());

  }

  /**
   * Constructs a Chart
   * @param context the context of the activity
   * @return        the GraphicalView of the chart
   */
  public GraphicalView getView(Context context) {
    PlayerList.getInstance().players = (ArrayList<Player>)MemoryPlayerDAO.getInstance(context).getAllPlayers();
    String[] titles = new String[]{ "% of hitting a pair"};
    
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[PlayerList.getInstance().players.size()]);
    /* compute the pair hit percentage for each player */
    for(int i = 0;i < PlayerList.getInstance().players.size(); i++){
      values.get(0)[i] = Math.round(((double)PlayerList.getInstance().players.get(i).hit / 
                         (double)PlayerList.getInstance().players.get(i).turn) * 100.0);
    }
    
    
    int[] colors = new int[] { Color.YELLOW };
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    setChartSettings(renderer, "", "Player", "% of hitting a pair", 0.5,
        12.5, 0, 100, Color.GRAY, Color.LTGRAY);
    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);

    renderer.setXLabels(0);
    for(int i = 1; i <= PlayerList.getInstance().players.size(); i++){
      renderer.addXTextLabel(i, PlayerList.getInstance().players.get(i-1).toString());
    }
    renderer.setYLabels(10);
    renderer.setXLabelsAlign(Align.CENTER);
    renderer.setYLabelsAlign(Align.CENTER);
    renderer.setPanEnabled(true, false);
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setPointSize(5f);
    renderer.setXLabelsAngle(45f);
    renderer.setMargins(new int[] { 20, 30, 15, 20 });

    renderer.setBarSpacing(0.5f);
    return ChartFactory.getBarChartView(context, buildBarDataset(titles, values), renderer,
        Type.DEFAULT);
  }
  
  
  /**
   * Constructs the renderer for each bar
   * @param colors an array of colors for the bars
   * @return a XYMultipleSeriesRenderer
   */
  private XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(colors[i]);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }
  
  /**
   * Collects the given data and assings it to a dataset
   * @param titles the title of each bar
   * @param values the value of each bar
   * @return the dataset of the graph
   */
  private XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      CategorySeries series = new CategorySeries(titles[i]);
      double[] v = values.get(i);
      int seriesLength = v.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(v[k]);
      }
      dataset.addSeries(series.toXYSeries());
    }
    return dataset;
  }
  
  /**
   * Sets some Settings of the Chart
   * @param renderer      a XYMultipleSeriesRenderer
   * @param title         the title of the graph
   * @param xTitle        the title of the xAxis
   * @param yTitle        the title of the yAxis
   * @param xMin          the Minimum Value of the xAxis
   * @param xMax          the Maximum Value of the xAxis
   * @param yMin          the Minimum Value of the yAxis
   * @param yMax          the Maximum Value of the yAxis
   * @param axesColor     the Color of the two axes
   * @param labelsColor   the Color of the labels
   */
  private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
      int labelsColor) {
    renderer.setChartTitle(title);
    renderer.setXTitle(xTitle);
    renderer.setYTitle(yTitle);
    renderer.setXAxisMin(xMin);
    renderer.setXAxisMax(xMax);
    renderer.setYAxisMin(yMin);
    renderer.setYAxisMax(yMax);
    renderer.setAxesColor(axesColor);
    renderer.setLabelsColor(labelsColor);
  }

}