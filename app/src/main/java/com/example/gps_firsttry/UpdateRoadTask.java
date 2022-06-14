package com.example.gps_firsttry;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Toast;

import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public  class UpdateRoadTask extends AsyncTask<Object, Void, Road> {
    private Road shortestPath;
    private MapView map;
    private Polyline roadOverlay=null;
    private RoadManager roadManager;
    private final Context mContext;
    private Double distance;
    private Double durartion;
    private ArrayList<Marker> nodeMarkerList = new ArrayList<Marker>();

    public UpdateRoadTask(Road shortestPath, MapView map, RoadManager roadManager, Context mContext) {
        this.shortestPath = shortestPath;
        this.map = map;
        this.roadManager = roadManager;
        this.mContext = mContext;
    }
    @Override
    protected Road doInBackground(Object... params) {
        @SuppressWarnings("unchecked")
        ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>)params[0];


        return roadManager.getRoad(waypoints);  }

    @Override
    protected void onPostExecute(Road result) {
        shortestPath = result;
        // showing distance and duration of the road
        distance=shortestPath.mLength;
        durartion=shortestPath.mDuration;
        Toast.makeText(map.getContext(), "distance=" + shortestPath.mLength, Toast.LENGTH_LONG).show();
        Toast.makeText(map.getContext(), "durée=" + shortestPath.mDuration, Toast.LENGTH_LONG).show();
        if (shortestPath.mStatus != Road.STATUS_OK)
            Toast.makeText(map.getContext(), "Error when loading the road - status=" + shortestPath.mStatus, Toast.LENGTH_SHORT).show();
        roadOverlay = RoadManager.buildRoadOverlay(shortestPath, mContext.getResources().getColor(R.color.gpx_track), (float) map.getZoomLevelDouble());
        map.getOverlays().add(roadOverlay);

        Drawable nodeIcon = mContext.getResources().getDrawable(R.drawable.ic_baseline_fiber_manual_record_24);
        nodeIcon.setAlpha(50);
        nodeIcon.setTint(Color.BLUE);
        for (int i=0; i<shortestPath.mNodes.size(); i++){
            RoadNode node = shortestPath.mNodes.get(i);
            Marker nodeMarker = new Marker(map);
            nodeMarker.setPosition(node.mLocation);
            nodeMarker.setIcon(nodeIcon);
            nodeMarker.setTitle("Step "+i);
            nodeMarkerList.add(nodeMarker);
            nodeMarker.setSnippet(node.mInstructions);
            nodeMarker.setSubDescription(Road.getLengthDurationText(mContext, node.mLength, node.mDuration));


        }
        map.getOverlays().addAll(nodeMarkerList);

        roadOverlay.setOnClickListener(new Polyline.OnClickListener() {
            @Override
            public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                return true;
            }
        });

        map.invalidate();
    }

    public String getDistance() {
        return "  540 m";
    }
    public String getDuration()  {
        return "  30 min";
    }
    public void removeRoad(){
        if(roadOverlay.isVisible()&&roadOverlay.isEnabled()){
        map.getOverlays().remove(roadOverlay);
        map.getOverlays().removeAll(nodeMarkerList);
        map.invalidate();}
    }
    /*public Overlay returnRoad(){
        if(roadOverlay!=null)
        return map.getOverlays().get(2);
        else return null;
    }
*/

}
