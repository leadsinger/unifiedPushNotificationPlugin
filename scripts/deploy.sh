#!/bin/bash

# set path to plugin directory
teamCityPluginPath=/Users/leadsinger_mac_mini/.BuildServer

# remove files before deployment
rm -rf $teamCityPluginPath/plugins/.unpacked
rm $teamCityPluginPath/plugins/*.zip

# copy the plugin
cp ../target/*.zip $teamCityPluginPath/plugins/