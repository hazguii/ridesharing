<script>
	import { enhance } from '$app/forms';
	import { Button, ButtonSet, Column, Grid, Loading, Row } from 'carbon-components-svelte';
    import { invalidateAll } from '$app/navigation';
	import DateTime from '$lib/components/DateTime.svelte';
	export let data;
	
	export let form;
</script>
{#if form}
   {form.message}
{/if}
{#await data.rsOffer}
<Loading />
{:then rsOffer}
<Grid>
	<Row>
	<Column>
		<h2>Details</h2>
	departure city : {rsOffer.departureAddress.city} ({rsOffer.departureGeoPoint.longitude},{rsOffer.departureGeoPoint.latitude})<br />
	destination city : {rsOffer.destinationAddress.city} ({rsOffer.destinationGeoPoint.longitude},{rsOffer.destinationGeoPoint.latitude})<br />
	time : <DateTime datetime={rsOffer.departureDateTime}/><br/>
	cancelled : {rsOffer.cancelled}<br />
	published : {rsOffer.published}

	<h2>Pricing</h2>
	{#if !rsOffer.published && !rsOffer.cancelled}
		Publish your offer to get pricingx
	{/if}
	{#if rsOffer.published}
		{#await data.rsOfferPricing}
			<Loading/>
		{:then pricing}
			<!-- pricing = {pricing?.price ?? 'price not available'} -->
			{#if pricing}
				pricing = {pricing.price}
			{:else}
				 pricing not available now
			{/if}
		{:catch error}
			{error}
		{/await}
	{/if}
</Column>
</Row>
<Row>
<Column>
	{#if !rsOffer.cancelled}
		<form method="POST" use:enhance>
			<input type="text" name="rsOfferId" value={rsOffer.rsOfferId.value} hidden />
			<ButtonSet>
				{#if rsOffer.published && !data.rsOfferPricing}
					<Button on:click={()=>invalidateAll()}>try pricing again</Button>
				{/if}
				{#if !rsOffer.published}
					<Button type="submit" formaction="?/publish">Publish</Button>
				{/if}
				<Button type="submit" formaction="?/cancel" kind="danger">Cancel</Button>
			</ButtonSet>
		</form>
	{/if}
</Column>
</Row>
</Grid>
{/await}
