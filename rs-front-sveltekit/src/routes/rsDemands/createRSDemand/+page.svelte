<script>
  import {enhance} from "$app/forms";
    import { DatePicker, DatePickerInput, Form, TextInput, TimePicker, TimePickerSelect, SelectItem, NumberInput } from "carbon-components-svelte";
    import {randomGeoPoint} from "$lib/api/rs-geo-functions"
 export let form;

 let departureGeoPoint = randomGeoPoint();
let destinationGeoPoint = randomGeoPoint();

</script>
  {#if form}
     {form.message}
  {/if}
  <form method="POST" use:enhance>

    <TextInput labelText="Departure address" name="departureAddress" value="tunis"/>
    <TextInput name="departureGeoPointLongitude" value="{departureGeoPoint.longitude}" hidden/>
    <TextInput name="departureGeoPointLatitude" value="{departureGeoPoint.latitude}" hidden/>
    <TextInput labelText="Destination address" name="destinationAddress" value="gabes"/>
    <TextInput name="destinationGeoPointLongitude" value="{destinationGeoPoint.longitude}" hidden/>
    <TextInput name="destinationGeoPointLatitude" value="{destinationGeoPoint.latitude}" hidden/>
    <DatePicker datePickerType="single" dateFormat="d/m/Y" > <!--bug dans dateFormat="Y-m-d" https://github.com/carbon-design-system/carbon-components-svelte/issues/1362-->
      <DatePickerInput labelText="Departure date" placeholder="dd/mm/yyy" name="departureDate"/>
    </DatePicker>
    <TimePicker labelText="Departure time" name="departureTime" placeholder="hh:mm">
      <TimePickerSelect value="pm" name="departureTimeAMPM">
        <SelectItem value="am" text="AM" />
        <SelectItem value="pm" text="PM" />
      </TimePickerSelect>
    </TimePicker>      
    <button>Create Demand</button>
  </form>