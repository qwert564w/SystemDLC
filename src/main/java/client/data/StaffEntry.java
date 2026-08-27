package client.data;

public record StaffEntry(String role, boolean vanished) {
   public boolean isVanished() {
      return this.vanished;
   }

   public String getRole() {
      return this.role;
   }
}
