import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: '/Jukebox',
  title: "Jukebox",
  description: "Minecraft Jukebox library",
  logo: 'https://raw.githubusercontent.com/IdanKoblik/Jukebox/refs/heads/pages/img/logo.png',
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Examples', link: '/markdown-examples' },
      { text: 'Spigot', link: '/TODO' },
      { text: 'Standalnoe', link: '/TODO' },
    ],
    socialLinks: [
      { icon: 'github', link: 'https://github.com/IdanKoblik/Jukebox/' },
      { icon: 'discord', link: 'https://discord.gg/pN4acjE5'}    
    ],
    footer: {
      message: "Released under the GNU gpl 3 License.",
      copyright: "Copyright © 2024-present Idan Koblik"
    },
    search: {
      provider: 'local'
    },
  }
})
