<script>
    import {enhance} from "$app/forms";
    import { AspectRatio, Button, Column, Grid,  Row, Tile } from "carbon-components-svelte";
	import { ArrowRight, Share, TaskRemove, TaskView } from "carbon-icons-svelte";
    export let rsDemands=[];

</script>
<Grid padding>
    <AspectRatio ratio="1x1">
        <Row>
        {#each rsDemands as rsDemand}
        <Column sm={2}>
            <Tile>
            {rsDemand.departureDateTime}
            {rsDemand.departureAddress.city} <ArrowRight/> {rsDemand.destinationAddress.city}
            {#if !rsDemand.cancelled}
            <form method="POST" use:enhance>
                <input type="text" name="rsDemandId" value="{rsDemand.rsDemandId.value}" hidden/>
                <Button href="/rsDemands/{rsDemand.rsDemandId.value}"  kind="ghost" icon={TaskView} iconDescription="See demand details" />
                {#if !rsDemand.published}
                        <Button type="submit" formaction="?/publish"  kind="ghost" icon={Share} iconDescription="Publish demand" />
                {/if}
                <Button type="submit" formaction="?/cancel" kind="ghost" icon={TaskRemove} iconDescription="Cancel demand" />
            </form> 
            {/if}
        </Tile>
    </Column>
    {/each}
</Row>
</AspectRatio>
</Grid>