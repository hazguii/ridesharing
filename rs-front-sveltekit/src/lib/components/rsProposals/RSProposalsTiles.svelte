<script>
    import { AspectRatio, Button, Column, Grid, Row, Tile } from "carbon-components-svelte";
	import { ArrowRight, Login, TaskView } from "carbon-icons-svelte";
    export let rsProposals=[];

   
</script>
<Grid padding>
    <AspectRatio ratio="1x1">
        <Row>
        {#each rsProposals as rsProposal}
        <Column sm={3}>
            <Tile>
                <!-- <div>{rsProposal.departureDateTime}</div> -->
                <!-- <div>{rsProposal.departureAddress.city} <ArrowRight/> {rsProposal.destinationAddress.city}</div> -->
                <div>accepted = {rsProposal.accepted}</div>
                <div>rejected = {rsProposal.rejected}</div>
                <div>pending = {!rsProposal.accepted && !rsProposal.rejected}</div>
                <div><Button href="/rsOffers/{rsProposal.rsOfferId.value}"  kind="ghost" icon={TaskView} iconDescription="See offer details" /> </div>
            </Tile>
        </Column>
        {/each}
    </Row>
    </AspectRatio>
</Grid>





<!-- <Grid fullWidth>
    <Row>
        {#each rsProposals as rsProposal}
            <AspectRatio ratio="2x1">
                <ClickableTile href="/rsProposals/{rsProposal.rsProposalId.value}">
                    {rsProposal.departureDateTime} : {rsProposal.departureAddress.city} -> {rsProposal.destinationAddress.city}
                </ClickableTile>
            </AspectRatio>
        {/each}
    </Row>
</Grid> -->

<!-- 
<table border='1'>
    <tr>
        <th>rsProposalId</th>
        <th>user</th>
        <th>departure</th>
        <th>destination</th>
        <th>time</th>
        <th>details</th>
    </tr>
{#each rsProposals as rsProposal}
    <tr>
        <td>{rsProposal.rsProposalId.value}</td>
        <td>{rsProposal.userId.id}</td>
        <td>{rsProposal.departureAddress.city}</td>
        <td>{rsProposal.destinationAddress.city}</td>
        <td>{rsProposal.departureDateTime}</td>
        <td><a href='rsProposals/{rsProposal.rsProposalId.value}'>voir</a></td>
    </tr>
{/each}
</table>
 -->
