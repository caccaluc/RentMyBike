<script>
    import { isAuthenticated, user } from "../../store";
  </script>
  
  <h1>Benutzer Details</h1>
  
  {#if $isAuthenticated}
    <p><img src={$user.picture} alt="" srcset="" /></p>
    <p><b>Vorname:</b> {$user.given_name}</p>
    <p><b>Nachname:</b> {$user.family_name}</p>
    <p><b>Email:</b> {$user.email}</p>
    
    <!-- Show roles only if user has at least one role -->
    {#if $user.user_roles && $user.user_roles.length > 0}
      <p><b>Roles:</b> {$user.user_roles}</p>
    {/if}
  {:else}
    <p>Du bist nicht angemeldet.</p>
  {/if}