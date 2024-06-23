<script>
  import "carbon-components-svelte/css/white.css";
  import {
    SideNavItems,
    SideNavMenuItem,
    SideNavDivider
  } from "carbon-components-svelte";
	import { Car, PassengerPlus } from "carbon-icons-svelte";
</script>
  <SideNavItems>
    <SideNavMenuItem icon={Car} href="/rsOffers/list" text="All offers"/>
    <SideNavDivider />
    <SideNavMenuItem  icon={PassengerPlus} href="/rsDemands/list" text="All demands"/>
  </SideNavItems>
