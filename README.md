Vaadin 7, Spring and JPA template project
=========================================

This is a template project for making Vaadin 7 play nice with Spring and JPA 
(EclipseLink using PostgreSQL in this example).

Some of our design goals (yours may differ):
* Maven managed, runs with "mvn jetty:run"
* Overcome the disparity between Spring's @WebApplicationScope and the tighter per-tab-or-window scope of Vaadin
* Always restore session where possible (new tab, closed window, etc)
* Minimize XML configuration (we originally tried to go fully annotation based but had to give up on that)

Vaadin really wants to manage its own sessions so the only way we could overcome 
a number of issues with that was by always creating a fresh new UI when 
attempting to restore a session state.

Known issues:
* There is a rarely occurring session timeout mismatch which for whatever reason becomes more prominent with
longer than the set 15 minute session timeout; when this happens the given
session gets stuck in an Exception state from which it will not recover until it times out fully

You are free to use this as a basis for your project or anything else, commercial or otherwise, 
we release this under the Apache 2.0 License 
(only so that you don't sue us when your processor melts because of something we did... ).

This work is attributed to Sonoport Ltd. (http://www.sonoport.com) as it was done 
in the context of an application we are building.


Copyright 2014 sonoport.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use these files except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
