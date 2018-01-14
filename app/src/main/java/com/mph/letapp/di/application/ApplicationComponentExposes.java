package com.mph.letapp.di.application;

import com.mph.letapp.di.application.module.DataModule;
import com.mph.letapp.di.application.module.InteractorModule;
import com.mph.letapp.di.application.module.MappersModule;
import com.mph.letapp.di.application.module.NetworkModule;
import com.mph.letapp.di.application.module.RepositoryModule;
import com.mph.letapp.di.application.module.ServiceModule;
import com.mph.letapp.di.application.module.ThreadingModule;

public interface ApplicationComponentExposes extends
        ApplicationModule.Exposes,
        MappersModule.Exposes,
        NetworkModule.Exposes,
        ServiceModule.Exposes,
        RepositoryModule.Exposes,
        InteractorModule.Exposes,
        DataModule.Exposes,
        ThreadingModule.Exposes {
}
