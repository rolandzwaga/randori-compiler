/***
 * Copyright 2013 Teoti Graphix, LLC.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * 
 * @author Michael Schmalle <mschmalle@teotigraphix.com>
 */

package randori.compiler.internal.driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.flex.compiler.clients.problems.ProblemQuery;
import org.apache.flex.compiler.units.ICompilationUnit;
import org.apache.flex.compiler.units.ICompilationUnit.UnitType;

import randori.compiler.config.IRandoriTargetSettings;
import randori.compiler.driver.IRandoriApplication;
import randori.compiler.driver.IRandoriBackend;
import randori.compiler.internal.config.annotation.AnnotationValidator;
import randori.compiler.internal.config.annotation.AnnotationVisitor;
import randori.compiler.internal.driver.model.ApplicationModel;
import randori.compiler.internal.projects.RandoriApplicationProject;
import randori.compiler.internal.visitor.as.ASWalker;
import randori.compiler.plugin.IPreProcessPlugin;
import randori.compiler.projects.IRandoriApplicationProject;

/**
 * @author Michael Schmalle
 */
public class RandoriApplication implements IRandoriApplication
{
    private final RandoriApplicationProject project;

    private ApplicationModel application;

    private List<ICompilationUnit> compilationUnits;

    private IRandoriTargetSettings settings;

    private ProblemQuery problems;

    private ASWalker annotationWalker;

    private ASWalker validatorWalker;

    public RandoriApplication(IRandoriApplicationProject project,
            List<ICompilationUnit> compilationUnits,
            IRandoriTargetSettings settings)
    {
        this.project = (RandoriApplicationProject) project;
        this.settings = settings;

        List<ICompilationUnit> units = new ArrayList<ICompilationUnit>();
        for (ICompilationUnit unit : compilationUnits)
        {
            if (unit.getCompilationUnitType() == UnitType.AS_UNIT)
                units.add(unit);
        }

        this.compilationUnits = units;

        application = new ApplicationModel(project, settings);

        annotationWalker = new ASWalker(new AnnotationVisitor(
                project.getAnnotationManager()));
        validatorWalker = new ASWalker(new AnnotationValidator(
                project.getAnnotationManager()));
    }

    @Override
    public boolean compile(IRandoriBackend backend, ProblemQuery problems)
    {
        this.problems = problems;

        filter();

        generate(backend);

        return true;
    }

    @Override
    public void analyze(ProblemQuery problems)
    {
        this.problems = problems;

        analyze();

        problems.addAll(project.getAnnotationManager().getProblems());
        problems.addAll(application.getProblems());
    }

    private void analyze()
    {
        if (project.getPluginFactory().hasPlugin(IPreProcessPlugin.class))
        {
            project.getProjectAccess().process();
        }

        try
        {
            compilationUnits = preprocess(compilationUnits);
            analyze(compilationUnits);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private List<ICompilationUnit> preprocess(List<ICompilationUnit> units)
            throws IOException
    {
        for (ICompilationUnit unit : units)
        {
            preprocess(unit);
        }
        return units;
    }

    private void analyze(List<ICompilationUnit> units) throws IOException
    {
        for (ICompilationUnit unit : units)
        {
            analyze(unit);
        }
    }

    private void preprocess(ICompilationUnit unit) throws IOException
    {
        if (project.getAnnotationManager().isEnabled())
        {
            annotationWalker.walkCompilationUnit(unit);
        }
    }

    private void analyze(ICompilationUnit unit) throws IOException
    {
        if (project.getAnnotationManager().isEnabled())
        {
            validatorWalker.walkCompilationUnit(unit);
        }
    }

    protected void filter()
    {
        application.filter(compilationUnits);
    }

    protected void generate(IRandoriBackend backend)
    {
        application.generate(backend, settings.getOutput());
        problems.addAll(application.getProblems());
    }

}
