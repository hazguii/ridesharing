<script>
	import { signIn, signOut } from '@auth/sveltekit/client';
	import { page } from '$app/stores';
	import AdminHeaderNav from '$lib/components/nav/AdminHeaderNav.svelte';
	import UserHeaderNav from '$lib/components/nav/UserHeaderNav.svelte';
	import AdminSideNav from '$lib/components/nav/AdminSideNav.svelte';
	import UserSideNav from '$lib/components/nav/UserSideNav.svelte';
	import UserUtilities from '$lib/components/nav/UserUtilities.svelte';
	import AdminUtilities from '$lib/components/nav/AdminUtilities.svelte';

	import 'carbon-components-svelte/css/white.css';
	import { Header,HeaderNav, SkipToContent, Content, SideNav ,HeaderUtilities,HeaderGlobalAction,HeaderAction,HeaderPanelLinks,HeaderPanelDivider,HeaderPanelLink,HeaderNavItem,SideNavLink,} from 'carbon-components-svelte';
	import { Home, Login, UserAvatarFilledAlt } from 'carbon-icons-svelte';



	let isSideNavOpen = false;
</script>

<Header company="ENIT" platformName="RS" bind:isSideNavOpen>
	<svelte:fragment slot="skip-to-content">
		<SkipToContent />
	</svelte:fragment>
	<HeaderNav>
    <HeaderNavItem href="/" text="Home" />
		{#if $page.data.session?.user?.roles.includes('rs-admin')}
			<AdminHeaderNav />
		{:else if $page.data.session?.user?.roles.includes('rs-user')}
			<UserHeaderNav />
		{/if}
	</HeaderNav>
  <HeaderUtilities>
    {#if $page.data.session}
    	<HeaderAction icon={UserAvatarFilledAlt} closeIcon={UserAvatarFilledAlt}>
			<HeaderPanelLinks>
				{#if $page.data.session?.user?.roles.includes('rs-admin')}
					<AdminUtilities/>
				{:else if $page.data.session?.user?.roles.includes('rs-user')}
					<UserUtilities/>
				{/if}
				<HeaderPanelDivider>Account</HeaderPanelDivider>
				<HeaderPanelLink href="http://localhost:8181/realms/rs-platform/account">Manage account</HeaderPanelLink>
				<HeaderPanelLink on:click={() => signOut()}>Sign out</HeaderPanelLink>
			</HeaderPanelLinks>
    	</HeaderAction>
    {:else}
		<HeaderGlobalAction aria-label="Login" icon={Login} on:click={() => signIn('keycloak')}/>
	{/if}
  </HeaderUtilities>
</Header>
<SideNav bind:isOpen={isSideNavOpen} rail>
  <SideNavLink icon={Home} href="/" text="Home" />
	{#if $page.data.session?.user?.roles.includes('rs-admin')}
		<AdminSideNav />
	{:else if $page.data.session?.user?.roles.includes('rs-user')}
		<UserSideNav />
	{/if}
</SideNav>
<Content>
	<slot />
</Content>
