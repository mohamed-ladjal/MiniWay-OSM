package com.example.gps_firsttry;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.Style;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.routing.GoogleRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.IOrientationConsumer;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Track;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.TrackSegment;


public class MainActivity extends AppCompatActivity  {
    private MapView map;
    private  BottomSheetDialog bottomSheetDialog ;
    private  BottomSheetDialog bottomSheetDialog2;
    private  BottomSheetDialog bottomSheetDialog3;
    private  BottomSheetDialog bottomSheetDialog4;
    private  BottomSheetDialog bottomSheetDialog5;
    private BottomSheetDialog bottomSheetDialogFavorites;
    private GpsTracker gpsTracker;
    private Button GoToMyLocation;
    private GeoPoint currentLocation;
    private GeoPoint endPoint;
    private GeoPoint startNode =null;
    private GeoPoint endNode=null;
    private CompassOverlay compassOverlay;
    private ScaleBarOverlay myScaleBarOverlay;
    private Button zoomIn;
    private Button zoomOut;
    private Button dir;
    private Button clear;
    private Button vehucle;
    private Button search;
    private Button favorite;
    private Button selectFromTheMap;
    private Button search1;
    private Button favorite1;
    private Button selectFromTheMap1;
    private Button MyLocation;
    private Button marque;
    private Button cancel;
    private Button go;
    private Button from ;
    private Button to;
    private Button foot;
    private Button car;
    private Button bike;
    private Button marke;
    private Button share;
    private Button info;
    private Button add;
    private Button navigate;
    private Button share1;
    private Button info1;
    private Button add1;
    private Button navigate1;
    private Button marques1;
    private Button delete;
    private Button searchButton;
    private Button btn;
    private Button openMenu;
    private Button cancelSearch;
    private AutoCompleteTextView editDeparture;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private  Button searchDepButton;
    private TextView duration;
    private TextView distanc;
    private LinearLayout searchBar;
    private String str1, str2,str3,str4;
    private String[] dirMenu = {"Ma position","Sélectionnez sur la carte", "Favoris","Marques"};
    private GeoPoint endPoint2 = new GeoPoint(35.3884177, 8.0955282);
    private GeoPoint endPoint3 = new GeoPoint(35.34, 8.1);
    private ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
    private List<GeoPoint> favorite_places = new ArrayList<>();
    private RoadManager roadManager = new OSRMRoadManager(MainActivity.this,"mouhamedLaadjel");
    private Road shortestPath;
   private  Polyline roadOverlay;
    private Overlay touchOverlay;
    UpdateRoadTask updateRoadTask;
    ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null;
    private Marker myMarker = null;
    private boolean selectFromtheMapAction = false;
    GeocoderNominatim geocoderNominatim = new GeocoderNominatim("mouhamed laadjel");
    boolean kase = false;
    private double speed =50;
    static String SHARED_PREFS_APPKEY = "OSMNavigator";
    static String PREF_LOCATIONS_KEY = "PREF_LOCATIONS";
    protected static int mINDEX=-3, START_INDEX=-2, DEST_INDEX=-1,RAND_INDEX=-3;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_main);
        setupMap();
        isStoragePermissionGranted();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       return super.onOptionsItemSelected(item);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setupMap() {
        map= findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        //map.setUseDataConnection(true);
        map.setMaxZoomLevel(22.0);
        map.setZoomRounding(true);
        map.setMinZoomLevel(3.0);
        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(false);
        compassOverlay = new CompassOverlay(this,map);
        compassOverlay.enableCompass();
        map.setCameraDistance(4);
        myScaleBarOverlay = new ScaleBarOverlay(map);
        myScaleBarOverlay.setTextSize(25);
        myScaleBarOverlay.setCentred(false);
        myScaleBarOverlay.setLineWidth(2);
        myScaleBarOverlay.setMaxLength(2);
        myScaleBarOverlay.setScaleBarOffset(myScaleBarOverlay.screenWidth/2 - myScaleBarOverlay.screenWidth/7,20);
        GoToMyLocation= findViewById(R.id.TrackB);
        clear= findViewById(R.id.clear);
        vehucle= findViewById(R.id.vehucule);
        duration= findViewById(R.id.duration);
        distanc = findViewById(R.id.distanc);
        duration.setVisibility(View.GONE);
        distanc.setVisibility(View.GONE);
        editDeparture = findViewById(R.id.editDeparture);
        zoomIn = findViewById(R.id.zoomIn);
        zoomOut= findViewById(R.id.zoomOut);
        openMenu=findViewById(R.id.menuOpen);
        searchDepButton = (Button)findViewById(R.id.buttonSearchDep);
        dir= findViewById(R.id.direction);
        searchButton=findViewById(R.id.search_button);
        bottomSheetDialogFavorites= new BottomSheetDialog(MainActivity.this);
        bottomSheetDialogFavorites.setContentView(R.layout.favorites);
        bottomSheetDialogFavorites.setCanceledOnTouchOutside(true);
        searchBar=findViewById(R.id.search_bar);
      cancelSearch=findViewById(R.id.cancelSearch);
    //    drawerLayout=findViewById(R.id.drawerlayout);
     //    navigationView=findViewById(R.id.menulayout);
       // drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
       // drawerLayout.addDrawerListener(drawerToggle);
       // drawerToggle.syncState();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //  drawerLayout.addDrawerListener((DrawerLayout.DrawerListener) openMenu);
      /*  navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Search123: { break;}
                    case  R.id.favorite1234:{break;}
                    case R.id.mesure: { break;}
                    case  R.id.mapConfig:{break;}
                    case R.id.about: { break;}
                    case  R.id.share1234:{break;}



                }
                return false;
            }
        });
*/
        getLocation();
        // latitude 35.395865 / longitude 8.108258
        endPoint2 = new GeoPoint(35.3884177,8.0955282);
        endPoint3 = new GeoPoint(35.34,8.1);
        str1=str2=str3=str4=null;
        //addStartMarker();
        IMapController mapcontroller = map.getController();
        mapcontroller.setZoom(7.0);
        if(currentLocation!=null){
        mapcontroller.setCenter(gpsTracker.marker.getPosition());
        map.getController().animateTo(gpsTracker.marker.getPosition(),20.0,3000L);
        str1=String.valueOf(currentLocation.getLatitude());
        str2=String.valueOf(currentLocation.getLongitude());}
      final MapEventsReceiver mReceive = new MapEventsReceiver(){
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                return false;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) {

                Marker marker = new Marker(map);
                marker.setPosition(p);
                marker.setIcon(getResources().getDrawable(R.drawable.redlocation));
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                map.getOverlays().add(marker);
                animateTo(map,p);
                bottomSheetDialog4= new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog4.setContentView(R.layout.marker);
                bottomSheetDialog4.setCanceledOnTouchOutside(true);
                marke = bottomSheetDialog4.findViewById(R.id.marke);
                share = bottomSheetDialog4.findViewById(R.id.share);
                add = bottomSheetDialog4.findViewById(R.id.add);
                info = bottomSheetDialog4.findViewById(R.id.info);
                navigate= bottomSheetDialog4.findViewById(R.id.navigation);
                bottomSheetDialog4.show();
                 marke.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        map.getOverlays().remove(marker);
                        addMarker(p);
                        bottomSheetDialog4.dismiss();
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReverseGeocodingTask reverseGeocodingTask = new ReverseGeocodingTask();
                        reverseGeocodingTask.execute(marker);
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,"Location :  "+marker.getSnippet());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View view) {
                        favorite_places.add(p);
                        bottomSheetDialog4.dismiss();

                        Button btn1 = bottomSheetDialogFavorites.findViewById(R.id.btn1);
                        LinearLayout root = bottomSheetDialogFavorites.findViewById(R.id.rootLayout);

                        btn1 = new Button(MainActivity.this);
                        btn1.setVisibility(View.VISIBLE);
                        btn1.setId(favorite_places.size());
                        btn1.setTextColor(Color.parseColor("#000000"));
                        btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        btn1.setGravity(Gravity.CENTER | Gravity.LEFT);
                        Drawable icon= getApplicationContext().getResources().getDrawable(R.drawable.favoriteicon2);
                        btn1.setBackgroundResource(R.drawable.roundbutton2);
                        icon.setBounds(0, 0, 0, 0); //Left,Top,Right,Bottom
                        btn1.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null);


                        root.addView(btn1);
                        PopUpClass popUpClass = new PopUpClass(bottomSheetDialogFavorites,btn1,MainActivity.this);
                        popUpClass.showPopupWindow(view);

                            btn1.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onClick(View view) {

                                   if(bottomSheetDialogFavorites.isShowing()) bottomSheetDialogFavorites.dismiss();
                                   if(bottomSheetDialog2.isShowing()) bottomSheetDialog2.dismiss();
                                    if(bottomSheetDialog3.isShowing())bottomSheetDialog3.dismiss();
                                    if (mINDEX == DEST_INDEX) {
                                        str3 = String.valueOf(p.getLatitude());
                                        str4 = String.valueOf(p.getLongitude());
                                        to.setText("À :\n   " + popUpClass.returntext());
                                        addMarker(p);
                                    }
                                    if (mINDEX == START_INDEX) {
                                        str1 = String.valueOf(p.getLatitude());
                                        str2 = String.valueOf(p.getLongitude());
                                        from.setText("De : \n   " + popUpClass.returntext());
                                    }


                                }
                            });
                            Toast.makeText(map.getContext(), "Vous avez ajouté le point à vos favoris", Toast.LENGTH_SHORT).show();
                            map.getOverlays().remove(marker);
                        }



                });
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog4.dismiss();
                        ReverseGeocodingTask reverseGeocodingTask = new ReverseGeocodingTask();
                        reverseGeocodingTask.execute(marker);


                    }
                });
                navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        str1=String.valueOf(currentLocation.getLatitude());
                        str2=String.valueOf(currentLocation.getLongitude());
                        str3=String.valueOf(p.getLatitude());
                        str4=String.valueOf(p.getLongitude());
                        mINDEX=RAND_INDEX;
                        drawRoad();
                        bottomSheetDialog4.dismiss();
                        map.getOverlays().remove(marker);
                        Marker marker2 = new Marker(map);
                        Drawable markerIcon =getResources().getDrawable(R.drawable.ic_baseline_location_on_24);
                        marker2.setPosition(p);
                        marker2.setIcon(markerIcon);
                        marker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                        map.getOverlays().add(marker2);
                    }
                });
                return true;
            }
        };
        map.getOverlays().add(new MapEventsOverlay(mReceive));




        map.getOverlays().add(myScaleBarOverlay);
        map.getOverlays().add(compassOverlay);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setVisibility(View.VISIBLE);
                mINDEX=RAND_INDEX;
                editDeparture.setFocusable(true);
                editDeparture.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editDeparture, InputMethodManager.SHOW_IMPLICIT);


            }
        });
        GoToMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myMarker!=null)
                animateTo(map,myMarker.getPosition());
                else Toast.makeText(map.getContext(), "unknown current location", Toast.LENGTH_LONG).show();
               //map.getOverlays().remove(updateRoadTask.returnRoad());
            }
        });
       clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map.getOverlays().size()>0)
                map.getOverlays().remove(map.getOverlays().get(map.getOverlays().size()-1));
                else map.getOverlays().clear();
                map.getOverlays().remove(gpsTracker.marker);
                map.getOverlays().add(gpsTracker.marker);
                map.getOverlays().add(new MapEventsOverlay(mReceive));

            }
        });

        clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                map.getOverlays().clear();
                duration.setVisibility(View.GONE);
                distanc.setVisibility(View.GONE);
                //map.getOverlays().addAll(i);
                map.getOverlays().add(compassOverlay);
                map.getOverlays().add(myScaleBarOverlay);
                map.getOverlays().remove(gpsTracker.marker);
                map.getOverlays().add(gpsTracker.marker);
                map.getOverlays().add(new MapEventsOverlay(mReceive));

                map.invalidate();
                return true;

            }
        });
        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(editDeparture.getText().toString().length()>0)
                   editDeparture.setText("");
                else{
                searchBar.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(editDeparture.getWindowToken(), 0);

               }
            }
        });
        searchDepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handleSearchButton(mINDEX, R.id.editDeparture);
            }
        });
      zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapcontroller.setZoom(map.getZoomLevelDouble()-1.0);
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapcontroller.setZoom(map.getZoomLevelDouble()+1.0);
            }
        });

        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog= new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottomsheetdialog);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
                 cancel = bottomSheetDialog.findViewById(R.id.cancel_button);
                 from = bottomSheetDialog.findViewById(R.id.goFrom);
                 to = bottomSheetDialog.findViewById(R.id.goTo);
                 go = bottomSheetDialog.findViewById(R.id.GO);
                 car = bottomSheetDialog.findViewById(R.id.car);
                 foot = bottomSheetDialog.findViewById(R.id.foot);
                 bike = bottomSheetDialog.findViewById(R.id.bike);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.cancel();
                        map.getOverlays().remove(map.getOverlays().get(map.getOverlays().size()-1));
                    }
                });
                car.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        speed = 60;
                        car.setBackgroundTintMode(PorterDuff.Mode.ADD);
                        bike.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                        foot.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                        vehucle.setForeground(getResources().getDrawable(R.drawable.ic_baseline_directions_car_24));
                        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_CAR);

                    }
                });
                bike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        speed =25;
                        bike.setBackgroundTintMode(PorterDuff.Mode.ADD);
                        car.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                        foot.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                        vehucle.setForeground(getResources().getDrawable(R.drawable.ic_baseline_directions_bike_24));
                        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
                    }
                });
                foot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        speed=5;
                        foot.setBackgroundTintMode(PorterDuff.Mode.ADD);
                        bike.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                        car.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                        vehucle.setForeground(getResources().getDrawable(R.drawable.ic_baseline_directions_walk_24));
                        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_FOOT);
                    }
                });
                from.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mINDEX=START_INDEX;
                        bottomSheetDialog2= new BottomSheetDialog(MainActivity.this);
                        bottomSheetDialog2.setContentView(R.layout.popup);
                        bottomSheetDialog2.setCanceledOnTouchOutside(true);
                        bottomSheetDialog2.show();
                        marque = bottomSheetDialog2.findViewById(R.id.marques);
                        search = bottomSheetDialog2.findViewById(R.id.Search);
                        MyLocation = bottomSheetDialog2.findViewById(R.id.myLocation);
                        favorite = bottomSheetDialog2.findViewById(R.id.favorite);
                        selectFromTheMap = bottomSheetDialog2.findViewById(R.id.selectFromTheMap);
                        search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                bottomSheetDialog2.dismiss();
                                bottomSheetDialog.dismiss();
                                searchBar.setVisibility(View.VISIBLE);
                                editDeparture.setFocusable(true);
                                mINDEX=START_INDEX;
                                editDeparture.setText("");
                                editDeparture.requestFocus();

                            }
                        });
                        MyLocation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheetDialog2.dismiss();
                               /* if (waypoints.size()>0)
                                    waypoints.remove(0);
                                waypoints.add(currentLocation);*/
                                str1=String.valueOf(currentLocation.getLatitude());
                                str2=String.valueOf(currentLocation.getLongitude());

                                from.setText("De : \n   Ma Position");


                            }
                        });
                        selectFromTheMap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                map.getOverlays().remove(shortestPath);
                                selectFromtheMapAction = true;
                                bottomSheetDialog2.dismiss();
                                bottomSheetDialog.dismiss();
                                selectFromTheMap("From");



                            }
                        });
                        favorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //bottomSheetDialogFavorites=new BottomSheetDialog(MainActivity.this);
                                //bottomSheetDialogFavorites.setContentView(R.layout.favorites);
                                //bottomSheetDialogFavorites.setCanceledOnTouchOutside(true);
                                bottomSheetDialogFavorites.show();
                            }
                        });
                        marque.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });

                    }
                });
                to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mINDEX=DEST_INDEX;
                        bottomSheetDialog3= new BottomSheetDialog(MainActivity.this);
                        bottomSheetDialog3.setContentView(R.layout.popup2);
                        bottomSheetDialog3.setCanceledOnTouchOutside(true);
                        bottomSheetDialog3.show();
                        marques1 =bottomSheetDialog3.findViewById(R.id.marques);
                        search1 = bottomSheetDialog3.findViewById(R.id.Search);
                        favorite1 = bottomSheetDialog3.findViewById(R.id.favorite);
                        selectFromTheMap1 = bottomSheetDialog3.findViewById(R.id.selectFromTheMap);
                        search1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               bottomSheetDialog3.dismiss();
                               bottomSheetDialog.dismiss();
                                searchBar.setVisibility(View.VISIBLE);
                                editDeparture.setFocusable(true);
                                editDeparture.setText("");
                                editDeparture.requestFocus();
                                mINDEX=DEST_INDEX;
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(editDeparture, InputMethodManager.SHOW_IMPLICIT);

                            }
                        });

                        selectFromTheMap1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                selectFromtheMapAction= true;
                                bottomSheetDialog3.dismiss();
                                bottomSheetDialog.dismiss();
                                selectFromTheMap("To");


                            }
                        });
                        favorite1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                bottomSheetDialogFavorites.show();

                            }
                        });

                    }
                });
                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){

                            /*map.getOverlays().add(anotherItemizedIconOverlay);
                            map.invalidate();
                            bottomSheetDialog.dismiss();
                            System.out.println(str1 + str2 + "jomana");
                            if(waypoints.size()==2)
                                waypoints.remove(1);
                            waypoints.add(gg);
                            UpdateRoadTask updateRoadTask =  new UpdateRoadTask(shortestPath,map,roadManager,MainActivity.this);
                            updateRoadTask.execute(waypoints);
                            duration.setVisibility(View.VISIBLE);
                            //duration.setText(String.valueOf(updateRoadTask.getDuration()));
                            distanc.setVisibility(View.VISIBLE);
                            //distanc.setText(String.valueOf(updateRoadTask.getDistance()));
                            map.invalidate();}*/
                        bottomSheetDialog.dismiss();

                        drawRoad();




                    }

                });
            }
        });



    }
    public void addPolyLine(List<GeoPoint> g) {
        Polyline line = new Polyline();   //see note below!
        line.setPoints(g);
        line.setColor(R.color.white);
        line.getDistance();
        line.setOnClickListener(new Polyline.OnClickListener() {
            @Override
            public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                Toast.makeText(mapView.getContext(), "polyline with " + polyline.getPoints().size() + "pts was tapped", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        map.getOverlayManager().add(line);
    }

    public void addStartMarker(){//add marker at my location
        Marker marker = new Marker(map);
        marker.setPosition(currentLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_CENTER);
//MapCircleOverlay mapCircleOverlay = new MapCircleOverlay(start);
  //      Canvas c = new Canvas();
//mapCircleOverlay.draw(c,map,true);
    //marker.setIcon(getResources().getDrawable(R.drawable.ic_baseline_adjust_24))
    marker.setRotation(360);
    map.getOverlays().clear();
    map.getOverlays().add(marker);
    map.invalidate();

}
@RequiresApi(api = Build.VERSION_CODES.O)
public void addMarker(GeoPoint geoPoint) {
    Marker marker = new Marker(map);
    Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.ic_baseline_golf_course_24);
    Random rand = new Random();
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    drawable.setTint(Color.rgb(r,g,b));
    drawable.setAlpha(15);
    marker.setPosition(geoPoint);
    marker.setIcon(drawable);
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
    map.getOverlays().add(marker);
    searchBar.setVisibility(View.GONE);
    bottomSheetDialog5= new BottomSheetDialog(MainActivity.this);
    bottomSheetDialog5.setContentView(R.layout.popup3);
    bottomSheetDialog5.setCanceledOnTouchOutside(true);
    delete = bottomSheetDialog5.findViewById(R.id.delete);
    share1 = bottomSheetDialog5.findViewById(R.id.share);
    add1 = bottomSheetDialog5.findViewById(R.id.add);
    info1 = bottomSheetDialog5.findViewById(R.id.info);
    navigate1 = bottomSheetDialog5.findViewById(R.id.navigation);
    if(mINDEX==RAND_INDEX)
    bottomSheetDialog5.show();
    delete.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            map.getOverlays().remove(marker);
            bottomSheetDialog5.dismiss();
        }
    });
    share1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ReverseGeocodingTask reverseGeocodingTask = new ReverseGeocodingTask();
            reverseGeocodingTask.execute(marker);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Location :  "+marker.getSnippet());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }
    });
    add1.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {
            bottomSheetDialog5.dismiss();

            Button btn1 = bottomSheetDialogFavorites.findViewById(R.id.btn1);
            LinearLayout root = bottomSheetDialogFavorites.findViewById(R.id.rootLayout);

            btn1 = new Button(MainActivity.this);
            btn1.setVisibility(View.VISIBLE);
            btn1.setId(favorite_places.size());
            btn1.setTextColor(Color.parseColor("#000000"));
            btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn1.setGravity(Gravity.CENTER | Gravity.LEFT);
            Drawable icon= getApplicationContext().getResources().getDrawable(R.drawable.favoriteicon2);
            btn1.setBackgroundResource(R.drawable.roundbutton2);
            icon.setBounds(0, 0, 0, 0); //Left,Top,Right,Bottom
            btn1.setCompoundDrawablesWithIntrinsicBounds( icon, null,null, null);
            root.addView(btn1);
            PopUpClass popUpClass = new PopUpClass(bottomSheetDialogFavorites,btn1,MainActivity.this);
            popUpClass.showPopupWindow(view);

            btn1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {

                    if(bottomSheetDialogFavorites.isShowing()) bottomSheetDialogFavorites.dismiss();
                    if(bottomSheetDialog2.isShowing()) bottomSheetDialog2.dismiss();
                    if(bottomSheetDialog3.isShowing())bottomSheetDialog3.dismiss();
                    if (mINDEX == DEST_INDEX) {
                        str3 = String.valueOf(geoPoint.getLatitude());
                        str4 = String.valueOf(geoPoint.getLongitude());
                        to.setText("À :\n   " + popUpClass.returntext());
                        addMarker(geoPoint);
                    }
                    if (mINDEX == START_INDEX) {
                        str1 = String.valueOf(geoPoint.getLatitude());
                        str2 = String.valueOf(geoPoint.getLongitude());
                        from.setText("De : \n   " + popUpClass.returntext());
                    }


                }
            });
            Toast.makeText(map.getContext(), "Vous avez ajouté le point à vos favoris", Toast.LENGTH_SHORT).show();
            map.getOverlays().remove(marker);
        }
    });
    info1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomSheetDialog5.dismiss();
            ReverseGeocodingTask reverseGeocodingTask = new ReverseGeocodingTask();
            reverseGeocodingTask.execute(marker);

        }
    });
    navigate1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomSheetDialog5.dismiss();
            str1=String.valueOf(currentLocation.getLatitude());
            str2=String.valueOf(currentLocation.getLongitude());
            str3=String.valueOf(geoPoint.getLatitude());
            str4=String.valueOf(geoPoint.getLongitude());
            drawRoad();
        }
    });
    marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker, MapView mapView) {

            bottomSheetDialog5.show();

            return  true;
        }
    });

}

@SuppressLint("ResourceAsColor")
public void animateTo(MapView map, IGeoPoint Point)  {
        map.getController().animateTo(map.getMapCenter(),3.0,1000L);
        map.getController().animateTo(Point,20.0,1400L);
}
    public void  getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this , map,updateRoadTask,endNode);
               if(gpsTracker.canGetLocation()){

             currentLocation= (new GeoPoint( gpsTracker.getLatitude(),gpsTracker.getLongitude()));
            myMarker =  gpsTracker.marker;

        }else{
            gpsTracker.showSettingsAlert();

        }
    }
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        } else {//permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission

        }
    }

    public boolean  selectFromTheMap (String where) {
         touchOverlay = new Overlay(this){
           @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {
                 Drawable marker7 =getResources().getDrawable(R.drawable.newlocation);
                //marker.setTint(Color.rgb(255,90,0));
               //marker.setTint(getResources().getColor(R.color.buy_button_color));
               if(selectFromtheMapAction){
                Projection proj = mapView.getProjection();
                GeoPoint loc = (GeoPoint) proj.fromPixels((int)e.getX(), (int)e.getY());
                String longitude = Double.toString(((double)loc.getLongitudeE6())/1000000);
                String latitude = Double.toString(((double)loc.getLatitudeE6())/1000000);
                System.out.println("- Latitude = " + latitude + ", Longitude = " + longitude );
                ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();


                    if(where =="From"){
                    str2 =longitude;
                    str1 = latitude;}
                   else if(where =="To") {
                        str4 =longitude;
                        str3 = latitude;
                    }
                 /*   if(where=="From" && longitude!=null && latitude!=null){
                    startNode.setLatitude(Double.parseDouble(str2));
                    startNode.setLongitude(Double.parseDouble(str1));
                    }
                    if(where=="To"){
                        endNode.setLatitude(Double.parseDouble(str2));
                        endNode.setLongitude(Double.parseDouble(str1));}
                    */
                   OverlayItem mapItem = new OverlayItem("", "", new GeoPoint((((double)loc.getLatitudeE6())/1000000),(((double)loc.getLongitudeE6())/1000000)));
                   mapItem.setMarker(marker7);

                   overlayArray.add(mapItem);
                   Toast.makeText(map.getContext(), "Lat = " + latitude + ", Long= " + longitude , Toast.LENGTH_SHORT).show();
                   anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray,null);
                   mapView.getOverlays().add(anotherItemizedIconOverlay);
                   mapView.invalidate();
                   bottomSheetDialog.show();
                   if(where == "To" )
                       to.setText("À :\n   "+str1 + ",  " + str2 );
                   if(where == "From")
                       from.setText("De : \n   "+str1 + ",  " + str2);

               }
               else{str1=str2=str3=str4=null;}

                kase = true;

                selectFromtheMapAction=false;

                return true;
            } };
     /*   if(str1!=null && str2!=null){
        GeoPoint gg = new GeoPoint(Double.parseDouble(str2),Double.parseDouble(str1));
        System.out.println(str1 + str2 + "jomana");
            if(waypoints.size()==2)
                waypoints.remove(1);
        waypoints.add(gg);
            UpdateRoadTask updateRoadTask =  new UpdateRoadTask(shortestPath,map,roadManager,MainActivity.this);
            updateRoadTask.execute(waypoints);
            map.invalidate();
        }*/


        map.getOverlays().add(touchOverlay);

        return kase  ;        }
    public void drawRoad(){
        if(str1!=null && str2!=null)
            startNode =new GeoPoint(Double.parseDouble(str1),Double.parseDouble(str2));
       else startNode= null;
        if(str3!=null && str4!=null)
            endNode =new GeoPoint(Double.parseDouble(str3),Double.parseDouble(str4));
       else endNode= null;
       if(str1!=null && str2!=null&&str3!=null && str4!=null) {
            double dist ;
            double dur ;
            map.invalidate();
            waypoints.clear();
            if(endNode !=null && startNode!=null ){
            waypoints.add(startNode);
            waypoints.add(endNode);
             updateRoadTask =  new UpdateRoadTask(shortestPath,map,roadManager,MainActivity.this);
            updateRoadTask.execute(waypoints);}
            duration.setVisibility(View.VISIBLE);
            distanc.setVisibility(View.VISIBLE);
            searchBar.setVisibility(View.GONE);
            dist=distance(startNode.getLatitude(),startNode.getLongitude(),endNode.getLatitude(),endNode.getLongitude(),'K');
            dur=dist/Double.valueOf(speed/2);
            String u = " H";
            if(dur<1){
                dur*=60;
                u = " min";
            }
            distanc.setText(String.valueOf(dist).substring(0,4)+ " Km");
            duration.setText(String.valueOf(dur).substring(0,4) + u);
            map.invalidate();
        }}




        private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            if (unit == 'K') {
                dist = dist * 1.609344;
            } else if (unit == 'N') {
                dist = dist * 0.8684;
            }
            return (dist);
        }

        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        /*::  This function converts decimal degrees to radians             :*/
        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        private double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        /*::  This function converts radians to decimal degrees             :*/
        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        private double rad2deg(double rad) {
            return (rad * 180.0 / Math.PI);
        }
    private class GeocodingTask extends AsyncTask<Object, Void, List<Address>> {
        int mIndex;
        protected List<Address> doInBackground(Object... params) {
            String locationAddress = (String)params[0];
            mIndex = (Integer)params[1];
            GeocoderNominatim geocoder = new GeocoderNominatim("mouhamed laadjel");
            geocoder.setOptions(true); //ask for enclosing polygon (if any)
            //GeocoderGraphHopper geocoder = new GeocoderGraphHopper(Locale.getDefault(), graphHopperApiKey);
            try {
                BoundingBox viewbox = map.getBoundingBox();
                List<Address> foundAdresses = geocoder.getFromLocationName(locationAddress, 20,
                        viewbox.getLatSouth(), viewbox.getLonEast(),
                        viewbox.getLatNorth(), viewbox.getLonWest(), false);
                return foundAdresses;
            } catch (Exception e) {
                return null;
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        protected void onPostExecute(List<Address> foundAdresses) {
            if (foundAdresses == null) {
                Toast.makeText(getApplicationContext(), "Geocoding error", Toast.LENGTH_SHORT).show();
            } else if (foundAdresses.size() == 0) { //if no address found, display an error
                Toast.makeText(getApplicationContext(), "Address not found.", Toast.LENGTH_SHORT).show();
            } else {
                Address address = foundAdresses.get(0); //get first address
                Log.d(TAG, "onPostExecute: barourou " +foundAdresses.size());
                String addressDisplayName = address.getExtras().getString("display_name");
                if (mIndex == START_INDEX){
                   // startPoint = new GeoPoint(address.getLatitude(), address.getLongitude());
                    str1 = String.valueOf(address.getLatitude());
                    str2 = String.valueOf(address.getLongitude());
                    map.getController().setCenter(new GeoPoint(Double.parseDouble(str1),Double.parseDouble(str2)));
                    addMarker(new GeoPoint(Double.parseDouble(String.valueOf(address.getLatitude())),Double.parseDouble(String.valueOf(address.getLongitude()))));
                    bottomSheetDialog.show();
                    from.setText("De :\n   "+addressDisplayName);
                } else if (mIndex == DEST_INDEX){
                    //destinationPoint = new GeoPoint(address.getLatitude(), address.getLongitude());
                  //  str1 = String.valueOf(currentLocation.getLatitude());
                   // str2 = String.valueOf(currentLocation.getLongitude());
                    str3 = String.valueOf(address.getLatitude());
                    str4 = String.valueOf(address.getLongitude());
                    addMarker(new GeoPoint(Double.parseDouble(String.valueOf(address.getLatitude())),Double.parseDouble(String.valueOf(address.getLongitude()))));
                    map.getController().setCenter(new GeoPoint(Double.parseDouble(str3),Double.parseDouble(str4)));
                    bottomSheetDialog.show();
                    to.setText("À :\n   "+addressDisplayName);
                   // map.getController().setCenter(new GeoPoint(Double.parseDouble(str3),Double.parseDouble(str4)));
                }
               // drawRoad();
                else if ( mIndex==RAND_INDEX) {
                     Toast.makeText(MainActivity.this, addressDisplayName, Toast.LENGTH_LONG).show();
                      addMarker(new GeoPoint(Double.parseDouble(String.valueOf(address.getLatitude())),Double.parseDouble(String.valueOf(address.getLongitude()))));
                      animateTo(map,new GeoPoint(Double.parseDouble(String.valueOf(address.getLatitude())),Double.parseDouble(String.valueOf(address.getLongitude()))));


                }
                else {}
            }
        }
    }
    public void handleSearchButton(int index, int editResId){

        searchBar.setVisibility(View.GONE);
        EditText locationEdit = (EditText)findViewById(editResId);
        //Hide the soft keyboard:
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(locationEdit.getWindowToken(), 0);

        String locationAddress = locationEdit.getText().toString();

        if (locationAddress.equals("")){
            map.invalidate();
            return;
        }

        Toast.makeText(this, "Searching:\n"+locationAddress, Toast.LENGTH_LONG).show();
        AutoCompleteOnPreferences.storePreference(this, locationAddress, SHARED_PREFS_APPKEY, PREF_LOCATIONS_KEY);
        new GeocodingTask().execute(locationAddress, index);
    }
    private class ReverseGeocodingTask extends AsyncTask<Marker, Void, String> {
        Marker marker3;
        protected String doInBackground(Marker... params) {
            marker3 = params[0];
            return getAddress(marker3.getPosition());
        }
        protected void onPostExecute(String result) {
            marker3.setSnippet(marker3.getPosition() +"\n"+result);
            marker3.showInfoWindow();
        }
    }
    public String getAddress(GeoPoint p){
        GeocoderNominatim geocoder = new GeocoderNominatim("mouhamed laadjel");
        //GeocoderGraphHopper geocoder = new GeocoderGraphHopper(Locale.getDefault(), graphHopperApiKey);
        String theAddress;
        try {
            double dLatitude = p.getLatitude();
            double dLongitude = p.getLongitude();
            List<Address> addresses = geocoder.getFromLocation(dLatitude, dLongitude, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                int n = address.getMaxAddressLineIndex();
                for (int i=0; i<=n; i++) {
                    if (i!=0)
                        sb.append(", ");
                    sb.append(address.getAddressLine(i));
                }
                theAddress = sb.toString();
            } else {
                theAddress = null;
            }
        } catch (IOException e) {
            theAddress = null;
        }
        if (theAddress != null) {
            return theAddress;
        } else {
            return "";
        }
    }

}
