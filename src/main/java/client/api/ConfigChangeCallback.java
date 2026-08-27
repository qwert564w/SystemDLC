package client.api;

import client.data.Waypoint;

public interface ConfigChangeCallback {
   public void onChange(Waypoint waypoint, int count, int count2, int count3);
}
