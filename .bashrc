#
# Local .bashrc
# author: cerrealic

alias run='cd server && start start.bat && cd ..'
alias bl='../spigot-dev-scripts/build.sh cerspi-lib'
alias bll='bl && run'
alias bp='bump'

source ../spigot-dev-scripts/bump.sh
