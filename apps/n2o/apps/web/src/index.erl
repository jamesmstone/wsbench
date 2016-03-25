-module(index).
-compile(export_all).
-include_lib("nitro/include/nitro.hrl").
-include_lib("n2o/include/wf.hrl").

main() -> "index".

    
event(Q) ->
    wf:info(?MODULE,"Event(Q): ~p",[Q]).
