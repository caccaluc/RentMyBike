<script>
  import "./styles.css";
  import { isAuthenticated, user } from "../store";
  import auth from "../auth.service";
</script>

<nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">RentMyBike</a>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarNav"
      aria-controls="navbarNav"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon" />
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        {#if $isAuthenticated && $user.user_roles.includes("user") && $user.user_roles}
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="/rides/ridesOverview">Vermietung finden</a>
          </li>
        {/if}
        {#if $isAuthenticated && $user.user_roles.includes("user") && $user.user_roles}
          <li class="nav-item">
            <a class="nav-link" href="/rides/myRides">Meine Vermietungen</a>
          </li>
        {/if}
        {#if $isAuthenticated && $user.user_roles.includes("user") && $user.user_roles}
          <li class="nav-item">
            <a class="nav-link" href="/motorcycle/my">Meine Motorräder</a>
          </li>
        {/if}
        {#if $isAuthenticated && $user.user_roles.includes("admin") && $user.user_roles}
        <li class="nav-item">
          <a class="nav-link" href="/user/all">Benutzerverwaltung</a>
        </li>
        {/if}
        {#if $isAuthenticated && $user.user_roles.includes("admin") && $user.user_roles}
        <li class="nav-item">
          <a class="nav-link" href="/motorcycle/all">Motorräder</a>
        </li>
        {/if}
        {#if $isAuthenticated}
          <li class="nav-item">
            <a class="nav-link" href="/account">Profil</a>
          </li>
        {/if}
        <li class="nav-item">
          <a class="nav-link" href="/Info">Über uns</a>
        </li>
        
      </ul>
      <div class="d-flex">
        {#if $isAuthenticated}
          <span class="navbar-text me-2">{$user.name}</span>
          <button type="button" class="btn btn-primary" on:click={auth.logout}
            >Log Out</button>
        {/if}
        {#if !$isAuthenticated}
          <a href="/login" class="btn btn-primary">Anmelden</a>
        {/if}
        {#if !$isAuthenticated}
          <a href="/signup" class="btn btn-secondary ms-2">Registrieren</a>
        {/if}
      </div>
    </div>
  </div>
</nav>

<div class="container mt-3">
  <slot />
</div>
