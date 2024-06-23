<script>
        import {enhance} from "$app/forms";

        import { Grid,Button,ButtonSet, Column, Loading, Row } from "carbon-components-svelte";
	import RsProposalsTiles from "$lib/components/rsProposals/RSProposalsTiles.svelte";
	import DateTime from "$lib/components/DateTime.svelte";
        export let data;
        export let form;
</script>
{#if form}
   {form.message}
{/if}




{#await data.rsDemand}
<Loading />
{:then rsDemand}
<Grid>
        <Row>
                <Column>
                        <h2>Details</h2>
                        departure city : {rsDemand.departureAddress.city} ({rsDemand.departureGeoPoint.longitude},{rsDemand.departureGeoPoint.latitude})<br />
                        destination city : {rsDemand.destinationAddress.city} ({rsDemand.destinationGeoPoint.longitude},{rsDemand.destinationGeoPoint.latitude})<br />                
                        time : <DateTime datetime={rsDemand.departureDateTime}/><br/>
                        cancelled : {rsDemand.cancelled}<br/>
                        published : {rsDemand.published}
                </Column>
                </Row>
                <Row>
                <Column>
                        {#if !rsDemand.cancelled}
                                <form method="POST" use:enhance>
                                        <input type="text" name="rsDemandId" value="{rsDemand.rsDemandId.value}" hidden/>
                                        <ButtonSet>
                                                {#if !rsDemand.published}
                                                        <Button type="submit" formaction="?/publish">Publish</Button>
                                                {/if}
                                                <Button type="submit" formaction="?/cancel" kind="danger">Cancel</Button>
                                        </ButtonSet>
                                </form>
                        {/if}
                </Column>
        </Row>
        <Row>
                <Column>
                        <h2>Matching</h2>
                </Column>
        </Row>
        <Row>
                <Column>
                        {#if rsDemand.published && !rsDemand.cancelled}
                                {#await data.rsDemandMatchRequest}
                                        <Loading />
                                {:then rsDemandMatchRequest}
                                        {#if rsDemandMatchRequest}
                                                demand is matching = {rsDemandMatchRequest.isMatching}
                                        {:else}
                                                could not get matching request, try later
                                        {/if}
                                {:catch error}
                                        ERROR : {error}                           
                                {/await}
                        {/if}
                </Column>
        </Row>
        <Row>
                <Column>
                        <h2>Proposals</h2>
                </Column>
        </Row>
        <Row>
                <Column>
                        {#if rsDemand.published && !rsDemand.cancelled}
                                {#await data.rsDemandProposals}
                                        <Loading />
                                {:then rsDemandProposals}
                                        {#if rsDemandProposals}
                                        <RsProposalsTiles rsProposals={rsDemandProposals}/>
                                        {:else}
                                                could not get RsProposalsTiles, try later
                                        {/if}
                                {:catch error}
                                        ERROR : {error.message}                           
                                {/await}
                        {/if}
                </Column>
        </Row>
</Grid>
{/await}




