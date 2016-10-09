package com.urun.camera_test.Data;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.urun.camera_test.CameraAccess.CameraPreview;
import com.urun.camera_test.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends Activity{

    private Camera camera_front, camera_back;
    SurfaceView surfaceView_back,surfaceView_front;
    SurfaceHolder surfaceHolder_back,surfaceHolder_front;
    CameraPreview cameraPreview_back,cameraPreview_front;
    Button capture_back, capture_front;
    Camera.PictureCallback jpegcallback;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Camera preview from FRONT
        surfaceView_front = (SurfaceView) findViewById(R.id.camera_preview_front);
        surfaceHolder_front = surfaceView_front.getHolder();
        cameraPreview_front = new CameraPreview(getApplicationContext(),camera_front,surfaceHolder_front,0);
        surfaceHolder_front.addCallback(cameraPreview_front);
        surfaceHolder_front.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        surfaceView_back = (SurfaceView) findViewById(R.id.camera_preview_back);
        surfaceHolder_back = surfaceView_back.getHolder();
        cameraPreview_back = new CameraPreview(getApplicationContext(),camera_back,surfaceHolder_back,1);
        Toast.makeText(MainActivity.this, "CameraPreview Object has been created", Toast.LENGTH_SHORT).show();
        surfaceHolder_back.addCallback(cameraPreview_back);
        surfaceHolder_back.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        capture_front = (Button) findViewById(R.id.button_capture_back);
        capture_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cameraPreview_front.takePic();
                if(cameraPreview_front!=null) {
                    cameraPreview_front.camera.takePicture(null,null,new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            FileOutputStream outStream = null;
                            try {
                                outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                                outStream.write(data);
                                outStream.close();
                                Log.d("picture_saved", "Picture has been saved succesfully: " + data.length);
                                camera.release();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Log.d("file_not_found: ","couldn't save the file "+e.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("IOexception: ","couldn't save the file "+e.getMessage());
                            } finally {
                            }
                            Log.d("Log", "onPictureTaken - jpeg");
                        }
                    });
                    Toast.makeText(MainActivity.this, "Picture Has Been Taken", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Failed!!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        capture_back = (Button) findViewById(R.id.button_capture_front);
        capture_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraPreview_back!=null) {
                    cameraPreview_back.camera.takePicture(null,null,new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            FileOutputStream outStream = null;
                            try {
                                outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                                outStream.write(data);
                                outStream.close();
                                Log.d("picture_saved", "Picture has been saved succesfully: " + data.length);
                                camera.release();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Log.d("file_not_found: ","couldn't save the file "+e.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("IOexception: ","couldn't save the file "+e.getMessage());
                            } finally {
                            }
                            Log.d("Log", "onPictureTaken - jpeg");
                        }
                    });
                    Toast.makeText(MainActivity.this, "Picture Has Been Taken", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Failed!!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(camera_back != null){
            camera_back.stopPreview();
            camera_back.release();
            camera_back = null;
        }
        if(camera_front != null){
            camera_front.stopPreview();
            camera_front.release();
            camera_front = null;
        }
    }
}
