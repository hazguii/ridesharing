<script>
  import "carbon-components-svelte/css/white.css";
  import {
    HeaderNavItem,
    HeaderNavMenu
  } from "carbon-components-svelte";
</script>
    <HeaderNavMenu text="Driver">
      <HeaderNavItem href="/rsOffers/list" text="My offers" />
      <HeaderNavItem href="/rsOffers/createRSOffer" text="New Offer" />
    </HeaderNavMenu> 
    <HeaderNavMenu text="Rider">
      <HeaderNavItem href="/rsDemands/list" text="My demands" />
      <HeaderNavItem  href="/rsDemands/createRSDemand" text="New demand" />
    </HeaderNavMenu>       
    <HeaderNavItem  href="" text="Schedule" />
    <HeaderNavItem  href="" text="Messaging" />
