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
      { text: 'Spigot', link: '/spigot' },
    ],
    sidebar: {
      '/spigot/': [ 
        {
          text: 'Guide',
          collapsed: true,
          items: [
            { text: 'Install', link: '/spigot/' },
            { text: 'Instrument Manager', link: '/spigot/instrumentManager' },
            { text: 'Playing music', items: [
              { text: 'Song', link: '/spigot/song/song' },
              { text: 'Loopable song', link: '/spigot/song/loopableSong' },
              { text: 'Song queue', link: '/spigot/songQueue' },
            ]},
          ]
        }
      ]
    },
    socialLinks: [
      { icon: 'github', link: 'https://github.com/IdanKoblik/Jukebox/' },
      { icon: 'discord', link: 'https://discord.gg/pN4acjE5'}    
    ],
    footer: {
      message: "Released under the MIT License.",
      copyright: "Copyright © 2024-present Idan Koblik"
    },
    search: {
      provider: 'local'
    },
    lastUpdated: true,
  }
})
