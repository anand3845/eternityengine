package entities;


import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class ThirdPersonCamera extends Camera{
	
	private float distanceFromPlayer = 150;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, -100, 0);
	private float pitch = 10;
	private float yaw ;
	private float roll;
	
	private Player player;
		
	public ThirdPersonCamera(Player player){
		this.player = player;
	}
	
	public void move(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		
		calculateCameraPosition(horizontalDistance, verticalDistance);
		
		this.yaw = 180 - (player.getRotY() - angleAroundPlayer);
		
	}
	
	

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
		
		float theta = player.getRotY() +  angleAroundPlayer;
		float offsetX = (float) (horizontalDistance + Math.sin(Math.toRadians(theta))); 
		float offsetZ = (float) (horizontalDistance + Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.y = player.getPosition().y + horizontalDistance;
		position.z = player.getPosition().z - offsetZ;
		
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer-= zoomLevel; 
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY() *0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX() *0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

}
