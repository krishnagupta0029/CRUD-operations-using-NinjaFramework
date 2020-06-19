/**
 * Copyright (C) 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.util.List;
import java.util.Map;

import models.Article;
import models.Project;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import dao.ArticleDao;
import dao.ProjectDao;
import dao.SetupDao;

public class ApplicationController {

    @Inject
    ArticleDao articleDao;
    @Inject
    ProjectDao projectDao;
    @Inject
    SetupDao setupDao;
    LoginLogoutController controller = new LoginLogoutController();

    public ApplicationController() {
    }

    /**
     * Method to put initial data in the db...
     * 
     * @return
     */
    
    public Result setup() {
        setupDao.setup();
        return Results.ok();
    }

    
    public Result index() {

        List<Project> frontPost = projectDao.getFirstProjectForFrontPage();

        List<Project> olderPosts = projectDao.getOlderProjectsForFrontPage();
        Long iid = controller.getcuser();
        if (iid!= 0L) {
        	frontPost = null;
        }
        Map<String, Object> map = Maps.newHashMap();
//        map.put("frontProject", frontPost);
        map.put("olderProjects", olderPosts);
        return Results.html().render("frontProject", frontPost)
                .render("olderProjects", olderPosts);

    }
    public Result index1() {

        List<Project> frontPost = projectDao.getFirstProjectForFrontPage();

        List<Project> olderPosts = projectDao.getOlderProjectsForFrontPage();
        Long iid = controller.getcuser();
        if (iid!= 0L) {
        	frontPost = null;
        }
        Map<String, Object> map = Maps.newHashMap();
//        map.put("frontProject", frontPost);
        map.put("olderProjects", olderPosts);
        return Results.html().template("/views/ApplicationController/show.ftl.html").render("frontProject", frontPost)
                .render("olderProjects", olderPosts);

    }

}
