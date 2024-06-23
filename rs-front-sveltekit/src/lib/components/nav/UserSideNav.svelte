<script>
  import "carbon-components-svelte/css/white.css";
  import {
    SideNavItems,
    SideNavMenu,
    SideNavMenuItem,
    SideNavDivider,
	SideNavLink
  } from "carbon-components-svelte";
	import { Calendar, Car, Email, FlaggingTaxi, PassengerPlus, Pedestrian, Road } from "carbon-icons-svelte";


</script>

  <SideNavItems>
    <SideNavMenu icon={Car} text="Driver">
      <SideNavMenuItem href="/rsOffers/list" text="My offers"/>
      <SideNavMenuItem href="/rsOffers/createRSOffer" text="New offer"/>
    </SideNavMenu>
    <SideNavDivider />
    <SideNavMenu icon={FlaggingTaxi} text="Rider">
      <SideNavMenuItem href="/rsDemands/list" text="My Demands"/>
      <SideNavMenuItem href="/rsDemands/createRSDemand" text="New demand"/>
    </SideNavMenu>
    <SideNavDivider />
    <SideNavLink icon={Calendar}  text="My Schedule"/>
    <SideNavDivider />
    <SideNavLink icon={Email} text="Messaging"/>
  </SideNavItems>
