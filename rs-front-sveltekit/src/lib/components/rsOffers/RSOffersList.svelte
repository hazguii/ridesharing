<script>
    import { AspectRatio, Button, ClickableTile, Column, DataTable,Grid,Link, Row, StructuredList, StructuredListBody, StructuredListCell, StructuredListHead, StructuredListRow, Tile, TileGroup } from "carbon-components-svelte";
	import { ArrowRight, Login, TaskView } from "carbon-icons-svelte";
	import { text } from "svelte/internal";
    export let rsOffers=[];

   
</script>
<StructuredList condensed>
    <StructuredListHead>
      <StructuredListRow head>
        <StructuredListCell head>Time</StructuredListCell>
        <StructuredListCell head>Trip</StructuredListCell>
        <StructuredListCell head>Actions</StructuredListCell>
      </StructuredListRow>
    </StructuredListHead>
    <StructuredListBody>
        {#each rsOffers as rsOffer}
        <StructuredListRow>
            <StructuredListCell>{rsOffer.departureDateTime}</StructuredListCell>
            <StructuredListCell>{rsOffer.departureAddress.city} <ArrowRight/> {rsOffer.destinationAddress.city}</StructuredListCell>
            <StructuredListCell><Button href="/rsOffers/{rsOffer.rsOfferId.value}"  kind="ghost" icon={TaskView} iconDescription="See offer details" /> </StructuredListCell>
        </StructuredListRow>
        {/each}
    </StructuredListBody>
</StructuredList>





<!-- <Grid fullWidth>
    <Row>
        {#each rsOffers as rsOffer}
            <AspectRatio ratio="2x1">
                <ClickableTile href="/rsOffers/{rsOffer.rsOfferId.value}">
                    {rsOffer.departureDateTime} : {rsOffer.departureAddress.city} -> {rsOffer.destinationAddress.city}
                </ClickableTile>
            </AspectRatio>
        {/each}
    </Row>
</Grid> -->

<!-- 
<table border='1'>
    <tr>
        <th>rsOfferId</th>
        <th>user</th>
        <th>departure</th>
        <th>destination</th>
        <th>time</th>
        <th>details</th>
    </tr>
{#each rsOffers as rsOffer}
    <tr>
        <td>{rsOffer.rsOfferId.value}</td>
        <td>{rsOffer.userId.id}</td>
        <td>{rsOffer.departureAddress.city}</td>
        <td>{rsOffer.destinationAddress.city}</td>
        <td>{rsOffer.departureDateTime}</td>
        <td><a href='rsOffers/{rsOffer.rsOfferId.value}'>voir</a></td>
    </tr>
{/each}
</table>
 -->
